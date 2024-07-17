package edu.pnu.service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.DTO.AirApiResDTO;
import edu.pnu.DTO.RequestPredictDTO;
import edu.pnu.domain.Region;
import edu.pnu.persistence.AirPredictRepository;
import edu.pnu.persistence.PredictRequestRespository;
import edu.pnu.persistence.RegionRepository;

@Service
public class PredictService {
	@Autowired
	RegionRepository regionRepository;
	@Autowired
	PredictRequestRespository predictRequestRespository;
	@Autowired
	AirPredictRepository aireAirPredictRepository;
	@Autowired
	APIService apiService;
	@Autowired
	private RestTemplate restTemplate;
	@Value("${weather-api-key}")
	private String weather_api_key;
	
	public List<RequestPredictDTO> requestPredict() throws Exception {
		List<Region> regionList = regionRepository.findAll();
		List<RequestPredictDTO> requestDTOList = new ArrayList<>();
		int count = 0;
		
		String dataTerm = "MONTH";
		String date = "202407010100";
		String obs = "ta,hm,ws_10m,wd_10m,pa,rn_60m";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
		
		
		for(Region region:regionList) {
			System.out.println(++count);
			RequestPredictDTO reqDTO = null;
			URI uri = apiService.createAirURI(region.getStationName(), dataTerm);
			URI uri2 = createWeatherURI(date, obs, region.getDmY(), region.getDmX());
			
			ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
			ResponseEntity<String> response2 = restTemplate.getForEntity(uri2, String.class);

			String jsonRes = response.getBody();
			String jsonRes2 = response2.getBody();
			System.out.println(jsonRes);
			
			// ObjectMapper로 JSON 문자열을 객체로 받아오기
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(jsonRes);
			JsonNode itemsNode = root.path("response").path("body").path("items");
			
	        if (itemsNode.isArray()) {
	            Iterator<JsonNode> elements = itemsNode.elements();
	            while (elements.hasNext()) {
	                JsonNode item = elements.next();
	                if ("2024-07-01 01:00".equals(item.path("dataTime").asText())) {
	                	AirApiResDTO airDTO = objectMapper.treeToValue(item, AirApiResDTO.class);
	                	reqDTO = RequestPredictDTO.convertToDTO(airDTO);
	                	reqDTO = parseWeatherData(reqDTO, jsonRes2);
	                	break;
	                }
	            }
	        } else {
	            System.out.println("No items found.");
	        }
	        reqDTO.setIdx(count);
	        reqDTO.setStationName(region.getStationName());
	        requestDTOList.add(reqDTO);
		}
		

		System.out.println("Total Count: "+count);

		return requestDTOList;
		
	}
	
	private URI createWeatherURI(String date, String obs, String lon, String lat) throws Exception {
		String url = "https://apihub.kma.go.kr/api/typ01/url/sfc_nc_var.php";
		String serviceKey = weather_api_key;
		String encodedServiceKey;
		encodedServiceKey = URLEncoder.encode(serviceKey, "UTF-8");
		StringBuilder builder = new StringBuilder(url);
		builder.append("?" + URLEncoder.encode("tm1", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));
		builder.append("&" + URLEncoder.encode("tm2", "UTF-8") + "=" + URLEncoder.encode(date , "UTF-8"));
		builder.append("&" + URLEncoder.encode("obs", "UTF-8") + "=" + URLEncoder.encode(obs, "UTF-8"));
		builder.append("&" + URLEncoder.encode("itv", "UTF-8") + "=" + URLEncoder.encode("60", "UTF-8"));
		builder.append("&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8"));
		builder.append("&" + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8"));
		builder.append("&" + URLEncoder.encode("help", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
		builder.append("&" + URLEncoder.encode("authKey", "UTF-8") + "=" + encodedServiceKey);
		return new URI(builder.toString());
	}
	
	private static RequestPredictDTO parseWeatherData(RequestPredictDTO dto, String response) {
		// 응답에서 데이터 부분만 추출
//		System.out.println(response);
		String dataSection = response.split("# tm, ta, hm, ws_10m, wd_10m, pa, rn_60m")[1].trim();
		String[] lines = dataSection.split("\n");
		
		System.out.println(lines[0]);

		// 데이터 라인을 쉼표(,)로 분리
		String[] dataValues1 = lines[0].split(", ");

		// 데이터 값을 DTO에 설정
		dto.setTa(dataValues1[1]);
		dto.setHm(dataValues1[2]);
		dto.setWs(dataValues1[3]);
		dto.setWd(dataValues1[4]);
		dto.setAp(dataValues1[5]);
		dto.setRn(dataValues1[6]);

		return dto;
	}
}
