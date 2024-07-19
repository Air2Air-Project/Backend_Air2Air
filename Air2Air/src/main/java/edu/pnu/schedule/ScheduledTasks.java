package edu.pnu.schedule;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.DTO.AirApiResDTO;
import edu.pnu.DTO.AirDTO;
import edu.pnu.DTO.AllAirApiResDTO;
import edu.pnu.persistence.RegionRepository;
import edu.pnu.service.APIService;

//@Compon
@Service
public class ScheduledTasks {
	@Autowired
	private APIService apiService;
	@Autowired
	private RegionRepository regionRepository;
	
	
	@Scheduled(cron = "0 30 * * * *") 	// 매시각 50분에 호출
	public List<AirDTO> calculateIndex() throws Exception {
		System.out.println("이벤트 발생 시간: " + new java.util.Date());
		List<AirDTO> airList = getAirData();
		
		for(AirDTO air : airList) {
			if(!air.getNo2Value().equals("-") || !air.getSo2Value().equals("-") || !air.getCoValue().equals("-")) {				
				double cityPollutionIndex = calculateUrbanPollutionIndex(
						Double.parseDouble(air.getNo2Value()),
						Double.parseDouble(air.getSo2Value()),
						Double.parseDouble(air.getCoValue()));
				
				System.out.println(air.getStationName() + " 도시공해지수: " + cityPollutionIndex);
			}else {
				System.out.println(air.getStationName() + " 도시공해지수: 계산불가");
			}
			
		}
		return getAirData();
		
	}
	
	// Calculate AQI based on concentration and breakpoints
    public double calculateAQI(double concentration, double[] breakpoints) {
        double Clow = breakpoints[0];
        double Chigh = breakpoints[1];
        double Ilow = breakpoints[2];
        double Ihigh = breakpoints[3];

        return ((Ihigh - Ilow) / (Chigh - Clow)) * (concentration - Clow) + Ilow;
    }

    // Normalize the calculated AQI value
    public double normalize(double value, double min, double max) {
        return ((value - min) / (max - min)) * 100;
    }

    // Calculate AQI based on segments
    public double calculateSegmentedAQI(double concentration, double[][] breakpoints) {
        for (double[] bp : breakpoints) {
            if (concentration >= bp[0] && concentration <= bp[1]) {
                return calculateAQI(concentration, bp);
            }
        }
        return -1;  // Return an error value if concentration is out of range
    }

    // Calculate Urban Pollution Index with proper breakpoints
    public double calculateUrbanPollutionIndex(double no2, double so2, double co) {
        double[][] no2Breakpoints = {
            {0.0, 0.053, 0, 50}, {0.054, 0.1, 51, 100}, {0.101, 0.36, 101, 150},
            {0.361, 0.649, 151, 200}, {0.650, 1.249, 201, 300}, {1.25, 2.049, 301, 400},
            {2.05, 4.049, 401, 500}
        };
        double[][] so2Breakpoints = {
            {0.0, 0.035, 0, 50}, {0.036, 0.075, 51, 100}, {0.076, 0.185, 101, 150},
            {0.186, 0.304, 151, 200}, {0.305, 0.604, 201, 300}, {0.605, 1.004, 301, 400},
            {1.005, 2.004, 401, 500}
        };
        double[][] coBreakpoints = {
            {0.0, 4.4, 0, 50}, {4.5, 9.4, 51, 100}, {9.5, 12.4, 101, 150},
            {12.5, 15.4, 151, 200}, {15.5, 30.4, 201, 300}, {30.5, 40.4, 301, 400},
            {40.5, 50.4, 401, 500}
        };

        double aqiNo2 = calculateSegmentedAQI(no2, no2Breakpoints);
        double aqiSo2 = calculateSegmentedAQI(so2, so2Breakpoints);
        double aqiCo = calculateSegmentedAQI(co, coBreakpoints);

        double urbanPollutionIndex = 0.4 * aqiNo2 + 0.35 * aqiSo2 + 0.25 * aqiCo;
        System.out.println("aqiNo2: " + aqiNo2 + ", aqiSo2: " + aqiSo2 + ", aqiCo: " + aqiCo);
        // Normalize to 0-100 range
        double normalizedUrbanPollutionIndex = normalize(urbanPollutionIndex, 0, 500);
        return normalizedUrbanPollutionIndex;
    }

    // Calculate Air Quality Index with proper breakpoints
    public double calculateAirQualityIndex(double o3, double pm10, double pm25) {
        double[][] o3Breakpoints = {
            {0.0, 0.054, 0, 50}, {0.055, 0.070, 51, 100}, {0.071, 0.085, 101, 150},
            {0.086, 0.105, 151, 200}, {0.106, 0.200, 201, 300}
        };
        double[][] pm10Breakpoints = {
            {0.0, 54, 0, 50}, {55, 154, 51, 100}, {155, 254, 101, 150},
            {255, 354, 151, 200}, {355, 424, 201, 300}
        };
        double[][] pm25Breakpoints = {
            {0.0, 12, 0, 50}, {12.1, 35.4, 51, 100}, {35.5, 55.4, 101, 150},
            {55.5, 150.4, 151, 200}
        };

        double aqiO3 = calculateSegmentedAQI(o3, o3Breakpoints);
        double aqiPm10 = calculateSegmentedAQI(pm10, pm10Breakpoints);
        double aqiPm25 = calculateSegmentedAQI(pm25, pm25Breakpoints);

        double airQualityIndex = 0.3 * aqiO3 + 0.3 * aqiPm10 + 0.4 * aqiPm25;
        return normalize(airQualityIndex, 0, 500);  // Normalize to 0-100 range
    }

    public double calculateOutdoorActivityIndex(double urbanPollutionIndex, double airQualityIndex) {
        double outdoorActivityIndex = 0.4 * urbanPollutionIndex + 0.6 * airQualityIndex;
        return normalize(outdoorActivityIndex, 0, 100);
    }
    
    public List<AirDTO> getAirData() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
		URI uri = apiService.createAirAllURI();
		
		ResponseEntity<String> response = apiService.restTemplate.getForEntity(uri, String.class);
		String jsonRes = response.getBody();
		System.out.println(jsonRes);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(jsonRes);
		JsonNode itemsNode = root.path("response").path("body").path("items");
		
		List<AllAirApiResDTO> items = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<AllAirApiResDTO>>() {});
		List<AirDTO> listDTO = items.stream().map(AirDTO::convertToDTO).collect(Collectors.toList());

		return listDTO;
	}
}
