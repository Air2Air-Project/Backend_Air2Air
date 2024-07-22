package edu.pnu.service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import edu.pnu.DTO.AirPredictDTO;
import edu.pnu.domain.AirData;
import edu.pnu.domain.AirPredict;
import edu.pnu.domain.Region;
import edu.pnu.persistence.AirDataRepository;
import edu.pnu.persistence.AirPredictRepository;
import edu.pnu.persistence.PredictRequestRespository;
import edu.pnu.persistence.RegionRepository;

@Service
public class PredictService {
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private PredictRequestRespository predictRequestRespository;
	@Autowired
	private AirPredictRepository airePredictRepository;
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

	private Date predictionRequestTime; // 예측 요청 시간을 저장할 변수

//	 @Scheduled(cron = "0 0 * * * *")
	public String requestPredict() {
		try {
			// 날짜 포맷 설정
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = dateFormat.parse("2024-07-01 02:00");
			predictionRequestTime = date;

			// 데이터베이스에서 해당 날짜의 데이터 가져오기
			List<AirData> airList = airDataRepository.findByDateTime(date);
			airList.forEach(System.out::println);
			System.out.println("AirData list size: " + airList.size());

			// JSON 문자열로 변환
			String jsonPayload = objectMapper.writeValueAsString(airList);

			// HTTP 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// HTTP 엔터티 생성
			HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

			// POST 요청 보내기
			ResponseEntity<String> response = restTemplate.exchange("http://10.125.121.155:5000/predict",
					HttpMethod.POST, requestEntity, String.class);

			// 응답 확인
			String res = response.getBody();
			System.out.println("Response: " + res);
			processPredictionData(res);
			return res;

		} catch (Exception e) {
			e.printStackTrace();
			// 예외 처리 메시지
			System.out.println("An error occurred while processing the prediction request.");
			return null;
		}
	}

	public void processPredictionData(String jsonResponse) {
		try {
			JsonNode rootNode = objectMapper.readTree(jsonResponse);

			for (JsonNode stationNode : rootNode.path("prediction")) {
				String stationName = stationNode.path("stationName").asText();
				System.out.println(stationName);

				JsonNode coValues = stationNode.path("co");
				JsonNode no2Values = stationNode.path("no2");
				JsonNode o3Values = stationNode.path("o3");
				JsonNode pm10Values = stationNode.path("pm10");
				JsonNode pm25Values = stationNode.path("pm25");
				JsonNode so2Values = stationNode.path("so2");

				Region region = regionRepository.findBystationName(stationName).orElse(null);
				if (region == null) {
					continue; // 지역을 찾지 못한 경우 건너뜀
				}

				for (int i = 0; i < 3; i++) {
					Date predictedTime = getPredictedTime(i);
					Optional<AirPredict> existingPredict = airePredictRepository
							.findByRegionRegionIdAndPredictTime(region.getRegionId(), predictedTime);

					AirPredict predictData = existingPredict.orElse(new AirPredict());
					predictData.setRegion(region);
					predictData.setCo(coValues.get(i).asText());
					predictData.setNo2(no2Values.get(i).asText());
					predictData.setO3(o3Values.get(i).asText());
					predictData.setPm10(pm10Values.get(i).asText());
					predictData.setPm25(pm25Values.get(i).asText());
					predictData.setSo2(so2Values.get(i).asText());
					predictData.setPredictTime(predictedTime);

					airePredictRepository.save(predictData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurred while processing the prediction data.");
		}
	}

	private Date getPredictedTime(int hoursAhead) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(predictionRequestTime); // 예측 요청 시간을 기준으로 설정
		calendar.add(Calendar.HOUR, hoursAhead + 1); // +1 to get next hours
		return calendar.getTime();
	}

	public List<AirPredictDTO> getPredictData(String large, String middle, String small) throws Exception {
		Region region = regionRepository.findByLargeAndMiddleAndSmall(large, middle, small).orElse(null);
		if(region == null)
			return null;
		
		return findPredictData(region);
	}

	public List<AirPredictDTO> getPredictData(String stationName) throws Exception {
		Region region = regionRepository.findBystationName(stationName).orElse(null);
		if(region == null)
			return null;
		
		return findPredictData(region);
	}
	
	public List<AirPredictDTO> findPredictData(Region region) throws Exception{
		List<AirPredictDTO> airPredictList = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = dateFormat.parse("2024-07-01 01:00");
		predictionRequestTime = date;

		for(int i=0; i < 3; i++) {
			Date predictedTime = getPredictedTime(i);
			System.out.println(predictionRequestTime);
			AirPredict airPredict = airePredictRepository.findByRegionRegionIdAndPredictTime(region.getRegionId(), predictedTime).get();
			AirPredictDTO airPredictDto = AirPredictDTO.convertToDTO(airPredict);
			airPredictList.add(airPredictDto);
		}
		
		return airPredictList;
	}
}