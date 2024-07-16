package edu.pnu.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WindDTO {
	private String speed;
	private String deg;
	private String gust;
	private String stationName;
}
