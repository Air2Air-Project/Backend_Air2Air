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
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AirPredict {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	@ManyToOne
	@JoinColumn(name = "request_id", nullable = false)
	private PredictRequest request;
	
	@Builder.Default
	@Column(name = "predict_time", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date predictTime = new Date();
	
	private String pm25;
	private String pm10;
	private String o3;
	private String so2;
	private String no2;
	private String co;
}
