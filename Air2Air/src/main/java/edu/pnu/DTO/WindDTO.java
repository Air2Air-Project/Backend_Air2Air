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
//	"weather":[{"main":"Rain","description":"실 비",}],
//	"main":{"temp":24.99,"feels_like":26.1,"pressure":1005,"humidity":98,},
//	"wind":{"speed":2.83,"deg":214,"gust":6.26},"rain":{"1h":0.87},
	private String sky;
	private String skyDesc;
	private String temp;
	private String bodyTemp;
	private String press;
	private String humidity;
	private String rain1h;
	private String speed;
	private String deg;
	private String gust;
	private String stationName;
}
