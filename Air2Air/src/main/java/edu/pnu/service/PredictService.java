package edu.pnu.service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.DTO.AirApiResDTO;
import edu.pnu.DTO.AirDTO;
import edu.pnu.DTO.RequestPredictDTO;
import edu.pnu.domain.AirData;
import edu.pnu.domain.Region;
import edu.pnu.persistence.AirDataRepository;
import edu.pnu.persistence.AirPredictRepository;
import edu.pnu.persistence.PredictRequestRespository;
import edu.pnu.persistence.RegionRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class PredictService {
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private PredictRequestRespository predictRequestRespository;
	@Autowired
	private AirPredictRepository aireAirPredictRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private APIService apiService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AirDataRepository airDataRepository;
	@Value("${weather-api-key}")
	private String weather_api_key;

	public String requestPredict() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date date = dateFormat.parse("2024-07-01 01:00");

		List<AirData> airList = airDataRepository.findByDateTime(date);
//				List<FlaskReqDTO> weatherDTOList = weatherList.stream().map(FlaskReqDTO::convertToDTO)
//						.collect(Collectors.toList());
		for(AirData air : airList) {
			System.out.println(air);
		}
		System.out.println(airList.size());
		try {

            // JSON 문자열로 변환
            String jsonPayload = objectMapper.writeValueAsString(airList);

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HTTP 엔터티 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

            // POST 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(
                "http://10.125.121.218:5000/predict",
                HttpMethod.POST,
                requestEntity,
                String.class
            );

            // 응답 확인
            System.out.println("Response: " + response.getBody());
            return response.getBody();
        } catch (Exception e) {
//            e.printStackTrace();
        	System.out.println("서버가 켜져있지 않습니다.");
        }
		return null;
	}

//	 public List<RequestPredictDTO> requestPredict() throws Exception {
//	        List<Region> regionList = regionRepository.findAll();
//	        List<AirData> requestDTOList = new ArrayList<>();
//	        int count = 0;
//
//	        String dataTerm = "MONTH";
//	        String obs = "ta,hm,ws_10m,wd_10m,pa,rn_60m";
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
//
//	        ObjectMapper objectMapper = new ObjectMapper();
//	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
//	        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//
//	        String startDateStr = "202407010100";
//	        String endDateStr = "202407020000";
//
//	        for (Region region : regionList) {
//	            AirData reqDTO = null;
//	            URI airUri = apiService.createAirURI(region.getStationName(), dataTerm);
//	            URI weatherUri = createWeatherURI(startDateStr, endDateStr, obs, region.getDmY(), region.getDmX());
//
//	            // 한 번의 요청으로 한 달치 데이터를 가져옴
//	            String airJsonRes = makeApiRequest(airUri);
//	            if (airJsonRes == null) continue;
//	            System.out.println("Air Data: " + airJsonRes);
//
//	            // 한 번의 요청으로 하루치 데이터를 가져옴
//	            String weatherJsonRes = makeApiRequest(weatherUri);
//	            if (weatherJsonRes == null) continue;
//	            System.out.println("Weather Data: " + weatherJsonRes);
//
//	            JsonNode airRoot = objectMapper.readTree(airJsonRes);
//	            JsonNode airItemsNode = airRoot.path("response").path("body").path("items");
//
//	            for (int hour = 1; hour < 24; hour++) { // 01시부터 23시까지
//	                Calendar calendar = Calendar.getInstance();
//	                calendar.set(2024, Calendar.JULY, 1, hour, 0);
//	                Date date = calendar.getTime();
//	                String dateStr = dateFormat.format(date);
//	                String dataTimeStr = dateTimeFormat.format(date);
//
//	                System.out.println(++count + ": " + dateStr + " - " + dataTimeStr);
//
//	                // 한 달치 데이터에서 필요한 데이터를 추출
//	                if (airItemsNode.isArray()) {
//	                    Iterator<JsonNode> elements = airItemsNode.elements();
//	                    while (elements.hasNext()) {
//	                        JsonNode item = elements.next();
//	                        if (dataTimeStr.equals(item.path("dataTime").asText())) {
//	                            AirApiResDTO airDTO = objectMapper.treeToValue(item, AirApiResDTO.class);
//	                            reqDTO = AirData.convertToDTO(airDTO);
//	                            break;
//	                        }
//	                    }
//	                } else {
//	                    System.out.println("No items found.");
//	                }
//
//	                // 하루치 데이터에서 필요한 데이터를 추출
//	                if (reqDTO != null) {
//	                    reqDTO = parseWeatherData(reqDTO, weatherJsonRes, dateStr);
//	                }
//
//	                if (reqDTO != null) {
//	                    reqDTO.setStationName(region.getStationName());
//	                    reqDTO.setDatetime(date);
//	                    airDataRepository.save(reqDTO);
//	                }
//	            }
//	        }
//
//	        System.out.println("Total Count: " + count);
//
//	        return null;
//	}

	private String makeApiRequest(URI uri) {
		RestTemplate restTemplate = new RestTemplate();
		for (int attempt = 0; attempt < 5; attempt++) {
			try {
				ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
				String jsonRes = response.getBody();
				if (jsonRes.contains("SERVICE ERROR")) {
					System.out.println("Service error, retrying...");
					Thread.sleep(2000); // 2초 대기 후 재시도
					continue;
				}
				return jsonRes;
			} catch (Exception e) {
				System.out.println("Request failed, retrying...");
				try {
					Thread.sleep(2000); // 2초 대기 후 재시도
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					return null;
				}
			}
		}
		System.out.println("Failed to get valid response after multiple attempts");
		return null;
	}

	private AirData parseWeatherData(AirData dto, String response, String dateStr) {
		// 응답에서 데이터 부분만 추출
		String dataSection = response.split("# tm, ta, hm, ws_10m, wd_10m, pa, rn_60m")[1].trim();
		String[] lines = dataSection.split("\n");

		// 주어진 시간에 맞는 데이터 라인을 찾음
		for (String line : lines) {
			if (line.startsWith(dateStr)) {
				String[] dataValues = line.split(", ");

				// 데이터 값을 DTO에 설정
				dto.setTa(dataValues[1]);
				dto.setHm(dataValues[2]);
				dto.setWs(dataValues[3]);
				dto.setWd(dataValues[4]);
				dto.setAp(dataValues[5]);
				dto.setRn(dataValues[6]);
				break;
			}
		}

		return dto;
	}

	private URI createWeatherURI(String startDate, String endDate, String obs, String lon, String lat)
			throws Exception {
		String url = "https://apihub.kma.go.kr/api/typ01/url/sfc_nc_var.php";
		String serviceKey = weather_api_key;
		String encodedServiceKey = URLEncoder.encode(serviceKey, "UTF-8");
		StringBuilder builder = new StringBuilder(url);
		builder.append("?" + URLEncoder.encode("tm1", "UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8"));
		builder.append("&" + URLEncoder.encode("tm2", "UTF-8") + "=" + URLEncoder.encode(endDate, "UTF-8"));
		builder.append("&" + URLEncoder.encode("obs", "UTF-8") + "=" + URLEncoder.encode(obs, "UTF-8"));
		builder.append("&" + URLEncoder.encode("itv", "UTF-8") + "=" + URLEncoder.encode("60", "UTF-8"));
		builder.append("&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8"));
		builder.append("&" + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8"));
		builder.append("&" + URLEncoder.encode("help", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
		builder.append("&" + URLEncoder.encode("authKey", "UTF-8") + "=" + encodedServiceKey);
		return new URI(builder.toString());
	}
}
