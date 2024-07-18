package edu.pnu.DTO;

import java.util.Date;

import edu.pnu.domain.Alert;
import edu.pnu.domain.AlertType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlertDTO {
	private Long alertId;
	private RegionDTO region;
	private Date alertTime;
	private AlertType alertType;
	private String value;
	
	public static AlertDTO convertToDTO(Alert alert) {
		RegionDTO region = RegionDTO.builder()
				.large(alert.getRegion().getLarge())
				.middle(alert.getRegion().getMiddle())
				.small(alert.getRegion().getSmall())
				.build();
		
		String value = alert.getAlertType().equals(AlertType.AIR)
				? alert.getPollution().getAirPollution() : alert.getPollution().getCityPollution();
		
		AlertDTO dto = AlertDTO.builder()
				.alertId(alert.getAlertId())
				.region(region)
				.alertTime(alert.getAlertTime())
				.alertType(alert.getAlertType())
				.value(value)
				.build();
		return dto;
	}
}
