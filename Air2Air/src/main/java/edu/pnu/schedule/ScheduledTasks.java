package edu.pnu.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.pnu.DTO.AirDTO;
import edu.pnu.domain.AirData;
import edu.pnu.domain.PollutionIndex;
import edu.pnu.domain.Region;
import edu.pnu.persistence.AirDataRepository;
import edu.pnu.persistence.PollutionIndexRepository;
import edu.pnu.persistence.RegionRepository;
import edu.pnu.service.APIService;

//@Compon
@Service
public class ScheduledTasks {
	@Autowired
	private APIService apiService;
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private AirDataRepository airDataRepository;
	@Autowired
	private PollutionIndexRepository pollutionIndexRepository;
	
    private static final double PM10_STANDARD = 100.0;
    private static final double PM25_STANDARD = 50.0;
    private static final double O3_STANDARD = 0.1;
    
    private static final double SO2_STANDARD = 0.075;
    private static final double CO_STANDARD = 9;
    private static final double NO2_STANDARD = 0.1;
    
    private static final double W_PM25 = 0.5;
    private static final double W_PM10 = 0.4;
    private static final double W_O3 = 0.1;
    
    private static final double W_SO2 = 0.3;
    private static final double W_CO = 0.4;
    private static final double W_NO2 = 0.3;
    
    private static final double W_AQI = 0.6;
    private static final double W_CPI = 0.4;
	
    @Scheduled(cron = "0 0 * * * *")  // 매시각 정각에 호출
    public List<AirDTO> calculateIndex() throws Exception {
        System.out.println("이벤트 발생 시간: " + new java.util.Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // 2024-07-01로 고정하고 현재 시간을 기준으로 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        String formattedDate = dateFormat.format(date);
        System.out.println("요청 시간: " + formattedDate);

//		date = dateFormat.parse("2024-07-01 01:00");
        // 해당 시간에 이미 데이터가 존재하는지 확인
        List<PollutionIndex> existingData = pollutionIndexRepository.findByDatetime(date);
        if (!existingData.isEmpty()) {
            System.out.println("해당 시간에 이미 데이터가 존재합니다.");
            return null;  // 데이터가 존재하면 바로 반환
        }
        
        
		List<AirData> airList = airDataRepository.findByDateTime(date);
		
		for(AirData air : airList) {
			double airPollutionIndex = 0;
			double cityPollutionIndex = 0;
			double outDoorActivityIndex = 0;
			System.out.println("=".repeat(50));
			
		   
			if(!air.getO3().equals("-") & !air.getPm10().equals("-") & !air.getPm25().equals("-")) {			
				airPollutionIndex = calculateAQI(
						Double.parseDouble(air.getPm10()),
						Double.parseDouble(air.getPm25()),
						Double.parseDouble(air.getO3()));
				airPollutionIndex = Math.round(airPollutionIndex);
			}
	
			if(!air.getSo2().equals("-") & !air.getCo().equals("-") & !air.getNo2().equals("-")) {				
				cityPollutionIndex = calculateCPI(
						Double.parseDouble(air.getSo2()),
						Double.parseDouble(air.getCo()),
						Double.parseDouble(air.getNo2()));
				cityPollutionIndex = Math.round(cityPollutionIndex);	
			}
			
			outDoorActivityIndex = calculateOutdoorActivityIndex(cityPollutionIndex, airPollutionIndex);
			outDoorActivityIndex = Math.round(outDoorActivityIndex);
			

			
			System.out.println(air.getStationName() + " 공기공해지수: " + (int)airPollutionIndex);
			System.out.println(air.getStationName() + " 도시공해지수: " + (int)cityPollutionIndex);
			System.out.println(air.getStationName() + " 야외활동지수: " + (int)outDoorActivityIndex);
			
			System.out.println("=".repeat(50));
			
			Region region = regionRepository.findBystationName(air.getStationName()).get();
			String airPollutionIndexStr = Integer.toString((int) airPollutionIndex);
	        String cityPollutionIndexStr = Integer.toString((int) cityPollutionIndex);
	        String outDoorActivityIndexStr = Integer.toString((int) outDoorActivityIndex);
	        PollutionIndex pollutionIndex = PollutionIndex.builder()
	        		.airPollution(airPollutionIndexStr)
	        		.cityPollution(cityPollutionIndexStr)
	        		.outActivity(outDoorActivityIndexStr)
	        		.region(region)
	        		.datetime(date)
	        		.build();
	        
	        pollutionIndexRepository.save(pollutionIndex);
	        
			
		}
//		return getAirData();
		return null;
		
	}
	
	 public double calculateAQI(double pm10, double pm25, double o3) {
	        double pm10Normalized = pm10 / PM10_STANDARD;
	        double pm25Normalized = pm25 / PM25_STANDARD;
	        double o3Normalized = o3 / O3_STANDARD;

	        double aqi = (W_PM10 * pm10Normalized + W_PM25 * pm25Normalized + W_O3 * o3Normalized) / (W_PM10 + W_PM25 + W_O3) * 100;
	        return aqi;
	}
	 
	public double calculateCPI(double so2, double co, double no2) {
	        double so2Normalized = so2 / SO2_STANDARD;
	        double coNormalized = co / CO_STANDARD;
	        double no2Normalized = no2 / NO2_STANDARD;

	        double cpi = (W_SO2 * so2Normalized + W_CO * coNormalized + W_NO2 * no2Normalized) / (W_SO2 + W_CO + W_NO2) * 100;
	        return cpi;
	 }

	public double calculateOutdoorActivityIndex(double cpi, double aqi) {
		if(cpi == 0.0 || aqi == 0.0)
			return 0.0;
        double outdoorActivityIndex = (W_AQI * aqi + W_CPI * cpi) / (W_AQI + W_CPI);

        // Normalize to 0-100 range
        return normalize(outdoorActivityIndex, 0, 100);
    }

    // Normalize the calculated value to a 0-100 range
    private double normalize(double value, double min, double max) {
        return ((value - min) / (max - min)) * 100;
    }
    
//    public List<AirDTO> getAirData() throws Exception {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
//		URI uri = apiService.createAirAllURI();
//		
//		ResponseEntity<String> response = apiService.restTemplate.getForEntity(uri, String.class);
//		String jsonRes = response.getBody();
//		System.out.println(jsonRes);
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		JsonNode root = objectMapper.readTree(jsonRes);
//		JsonNode itemsNode = root.path("response").path("body").path("items");
//		
//		List<AllAirApiResDTO> items = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<AllAirApiResDTO>>() {});
//		List<AirDTO> listDTO = items.stream().map(AirDTO::convertToDTO).collect(Collectors.toList());
//
//		return listDTO;
//	}
}
