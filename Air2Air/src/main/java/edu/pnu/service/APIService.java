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
import edu.pnu.DTO.WindDTO;
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
	@Value("${weather-api-key}")
	private String weather_api_key;
	
	
	public AirDTO getAirAPI(String stationName) throws Exception {
		return getAirData(stationName);
	}
	
	public AirDTO getAirAPI(String large, String middle, String small) throws Exception {
		Region region = regionRepository.findByLargeAndMiddleAndSmall(large, middle, small).orElse(null);
		
		if(region == null) {
			System.out.println("large: " + large + ",middle: " + middle + ",small: " + small);
			System.out.println("해당 지역이 없습니다.");
			return null;
		}
		
		return getAirData(region.getStationName());
	}

	public WindDTO getWindAPI(String stationName) throws Exception {
		Region region = regionRepository.findBystationName(stationName).orElse(null);
		
		if(region == null) {
			System.out.println("stationName: " + stationName);
			System.out.println("해당 지역이 없습니다.");
			return null;
		}
		
		return getWindData(region);
	}

	public WindDTO getWindAPI(String large, String middle, String small) throws Exception {
		Region region = regionRepository.findByLargeAndMiddleAndSmall(large, middle, small).orElse(null);
		
		if(region == null) {
			System.out.println("large: " + large + ",middle: " + middle + ",small: " + small);
			System.out.println("해당 지역이 없습니다.");
			return null;
		}
		
		return getWindData(region);
	}
	
	public AirDTO getAirData(String stationName) throws Exception {
		System.out.println(stationName);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
		URI uri = createAirURI(stationName);
		
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
	
	
	public WindDTO getWindData(Region region) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
		
		URI uri = createWeatherURI(region.getDmX(), region.getDmY());
		// ObjectMapper로 JSON 문자열을 객체로 받아오기
		ObjectMapper objectMapper = new ObjectMapper();
	    ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String jsonRes = response.getBody();
//        System.out.println(jsonRes);

        // ObjectMapper로 JSON 문자열을 객체로 받아오기
        JsonNode root = objectMapper.readTree(jsonRes);
        JsonNode wind = root.path("wind");
        System.out.println(wind);

		// ObjectMapper로 JSON 문자열을 객체로 받아오기
        WindDTO windDTO = objectMapper.treeToValue(wind, WindDTO.class);
        windDTO.setStationName(region.getStationName());

        return windDTO;
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
	
	public URI createWeatherURI(String dmX, String dmY) throws UnsupportedEncodingException, URISyntaxException {
		String url = "https://api.openweathermap.org/data/2.5/weather";
		String serviceKey = weather_api_key;
		String encodedServiceKey;
		encodedServiceKey = URLEncoder.encode(serviceKey, "UTF-8");
		StringBuilder builder = new StringBuilder(url);
		builder.append("?" + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(dmX, "UTF-8"));
		builder.append("&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(dmY, "UTF-8"));
		builder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + encodedServiceKey);
		builder.append("&" + URLEncoder.encode("units", "UTF-8") + "=" + URLEncoder.encode("metric", "UTF-8"));
		builder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=" + URLEncoder.encode("kr", "UTF-8"));
		return new URI(builder.toString());
	}

}
