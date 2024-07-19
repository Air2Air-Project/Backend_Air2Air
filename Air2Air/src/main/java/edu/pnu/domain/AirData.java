package edu.pnu.domain;

import java.util.Date;

import edu.pnu.DTO.AirApiResDTO;
import edu.pnu.DTO.AirDTO;
import edu.pnu.DTO.RequestPredictDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AirData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	@Builder.Default
	@Temporal(value = TemporalType.TIMESTAMP)	
	private Date datetime = new Date();
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
	
	public static AirData convertToDTO(AirDTO air) {
		AirData dto = AirData.builder()
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

	public static AirData convertToDTO(AirApiResDTO air) {
		AirData dto = AirData.builder()
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
