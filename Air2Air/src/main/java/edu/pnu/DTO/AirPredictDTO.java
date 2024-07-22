package edu.pnu.DTO;

import java.util.Date;

import edu.pnu.domain.AirPredict;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirPredictDTO {
	private RegionDTO region;
	private Date predictTime;
	private String pm25;
	private String pm10;
	private String o3;
	private String so2;
	private String no2;
	private String co;
	
	public static AirPredictDTO convertToDTO(AirPredict predict) {
		if(predict == null)
			return null;
		
		RegionDTO region = RegionDTO.convertToDTO(predict.getRegion());
		
		AirPredictDTO dto = AirPredictDTO.builder()
				.region(region)
				.predictTime(predict.getPredictTime())
				.pm25(predict.getPm25())
				.pm10(predict.getPm10())
				.o3(predict.getO3())
				.so2(predict.getSo2())
				.no2(predict.getNo2())
				.co(predict.getCo())
				.build();
        
    	return dto;
    }
}
