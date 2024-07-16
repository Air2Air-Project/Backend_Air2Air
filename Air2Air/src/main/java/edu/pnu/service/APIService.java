package edu.pnu.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.DTO.AirApiResDTO;
import edu.pnu.DTO.AirDTO;
import edu.pnu.domain.Region;
import edu.pnu.persistence.RegionRepository;

@Service
public class APIService {
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Value("${decode-api-key}")
	private String decode_api_key;
	
	
	public AirDTO getAirAPI(String stationName) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
		URI uri = createAirURI(stationName);
//		System.out.println(stationName);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		String jsonRes = response.getBody();
		System.out.println(jsonRes);

		// ObjectMapper로 JSON 문자열을 객체로 받아오기
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(jsonRes);
		JsonNode itemsNode = root.path("response").path("body").path("items");
		List<AirApiResDTO> items = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<AirApiResDTO>>() {});
		AirDTO airDto = AirDTO.convertToDTO(items.get(0));
		airDto.setStationName(stationName);
		System.out.println(airDto);
		
		return airDto;
	}
	
	public AirDTO getAirAPI(String large, String middle, String small) throws Exception {
		Region region = regionRepository.findByLargeAndMiddleAndSmall(large, middle, small).orElse(null);
		
		if(region == null)
			return null;
		
		System.out.println(region.getStationName());
		
		
		return getAirAPI(region.getStationName());
	}
	
	public URI createAirURI(String stationName) throws UnsupportedEncodingException, URISyntaxException {
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty";
		String serviceKey = decode_api_key;
		String encodedServiceKey;
		encodedServiceKey = URLEncoder.encode(serviceKey, "UTF-8");
		StringBuilder builder = new StringBuilder(url);
		builder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + encodedServiceKey);
		builder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
		builder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8"));
		builder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
		builder.append("&" + URLEncoder.encode("stationName", "UTF-8") + "=" + URLEncoder.encode(stationName, "UTF-8"));
		builder.append("&" + URLEncoder.encode("dataTerm", "UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8"));
		builder.append("&" + URLEncoder.encode("ver", "UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8"));
		return new URI(builder.toString());
	}

	
}
