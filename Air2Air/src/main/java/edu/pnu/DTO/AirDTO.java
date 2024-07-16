package edu.pnu.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirDTO {
	private String o3Value;
	private String o3Grade;
	private String so2Value;
	private String so2Grade;
	private String no2Value;
	private String no2Grade;
	private String coValue;
	private String coGrade;
	private String pm25Value;
	private String pm25Grade;
	private String pm10Value;
	private String pm10Grade;
	private String stationName;
	
	public static AirDTO convertToDTO(AirApiResDTO air) {
		AirDTO dto = AirDTO.builder()
				.o3Value(air.getO3Value())
				.o3Grade(air.getO3Grade())
				.so2Value(air.getSo2Value())
				.so2Grade(air.getSo2Grade())
				.no2Value(air.getNo2Value())
				.no2Grade(air.getNo2Grade())
				.coValue(air.getCoValue())
				.coGrade(air.getCoGrade())
				.pm25Value(air.getPm25Value())
				.pm25Grade(air.getPm25Grade())
				.pm10Value(air.getPm10Value())
				.pm10Grade(air.getPm10Grade())
				.build();
        
    	return dto;
    }
}
