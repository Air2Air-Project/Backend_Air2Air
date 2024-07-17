package edu.pnu.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPredictDTO {
	private int idx;
	private String stationName;
	private String pm25;
	private String pm10;
	private String so2;
	private String o3;
	private String no2;
	private String co;
	private String ta;
	private String hm;
	private String ws;
	private String wd;
	private String ap;
	private String rn;
	
	public static RequestPredictDTO convertToDTO(AirDTO air) {
		RequestPredictDTO dto = RequestPredictDTO.builder()
				.stationName(air.getStationName())
				.pm25(air.getPm25Value())
				.pm10(air.getPm10Value())
				.so2(air.getSo2Value())
				.o3(air.getO3Value())
				.no2(air.getNo2Value())
				.co(air.getCoValue())
				.build();
		
		return dto;
	}

	public static RequestPredictDTO convertToDTO(AirApiResDTO air) {
		RequestPredictDTO dto = RequestPredictDTO.builder()
				.pm25(air.getPm25Value())
				.pm10(air.getPm10Value())
				.so2(air.getSo2Value())
				.o3(air.getO3Value())
				.no2(air.getNo2Value())
				.co(air.getCoValue())
				.build();
		
		return dto;
	}
}
