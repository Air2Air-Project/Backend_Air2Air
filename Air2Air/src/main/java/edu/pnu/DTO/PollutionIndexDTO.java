package edu.pnu.DTO;

import edu.pnu.domain.PollutionIndex;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PollutionIndexDTO {
	private int outActivity;
	private int cityPollution;
	private int airPollution;
	
	public static PollutionIndexDTO convertToDTO(PollutionIndex pollutionIndex) {
		PollutionIndexDTO dto = PollutionIndexDTO.builder()
				.outActivity(Integer.parseInt(pollutionIndex.getOutActivity()))
				.cityPollution(Integer.parseInt(pollutionIndex.getCityPollution()))
				.airPollution(Integer.parseInt(pollutionIndex.getAirPollution()))
				.build();
		
		return dto;
	}
}

