package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Long alertId;

	@ManyToOne
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;
	
	@Builder.Default
	@Column(name = "alert_time", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)	// datatype지정(이 코드에서는 timestamp)
	private Date alertTime = new Date();
	
	@Enumerated(EnumType.STRING)
    @Column(name = "alert_type",nullable = false)
	private AlertType alertType;
	
	@ManyToOne
	@JoinColumn(name = "pollution_id")
	private PollutionIndex pollution;
}
