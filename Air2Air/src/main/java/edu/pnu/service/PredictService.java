package edu.pnu.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.AirData;
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
	
//	 @Scheduled(cron = "0 0 * * * *")
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
}
