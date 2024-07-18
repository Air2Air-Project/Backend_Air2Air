package edu.pnu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PollutionIndex {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pollution_id")
	private Long pollutionId;
	
	@Column(name = "out_activity", nullable = false)
	private String outActivity;
	
	@Column(name = "city_pollution", nullable = false)
	private String cityPollution;
	
	@Column(name = "air_pollution", nullable = false)
	private String airPollution;
}
