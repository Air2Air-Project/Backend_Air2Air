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
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="region_id", nullable = false)
	private Long regionId;
	
	@Column(nullable = false)
	private String large;
	
	@Column(nullable = false)
	private String middle;
	
	@Column(nullable = false)
	private String small;
	
	@Column(name="station_name", nullable = false)
	private String stationName;
	
	@Column(nullable = false)
	private String dmX;
	
	@Column(nullable = false)
	private String dmY;
}
