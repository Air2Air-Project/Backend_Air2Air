package edu.pnu.ResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirApiResDTO {
	private String so2Grade;
	private String coFlag;
	private String khaiValue;
	private String so2Value;
	private String coValue;
	private String pm25Flag;
	private String pm10Flag;
	private String pm10Value;
	private String o3Grade;
	private String khaiGrade;
	private String pm25Value;
	private String no2Flag;
	private String no2Grade;
	private String o3Flag;
	private String pm25Grade;
	private String so2Flag;
	private String dataTime;
	private String coGrade;
	private String no2Value;
	private String pm10Grade;
	private String o3Value;
}
