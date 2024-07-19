package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn(name = "region_id")
	private Region region;
	
	@Builder.Default
	@Temporal(value = TemporalType.TIMESTAMP)	// datatype지정(이 코드에서는 timestamp)
	private Date datetime = new Date();
	
}
