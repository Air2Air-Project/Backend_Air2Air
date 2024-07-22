package edu.pnu.DTO;

import edu.pnu.domain.Region;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionDTO {
	private String large;
	private String middle;
	private String small;
	
	public static RegionDTO convertToDTO(Region region) {
		if(region == null)
			return null;
		
		RegionDTO dto = RegionDTO.builder()
				.large(region.getLarge())
				.middle(region.getMiddle())
				.small(region.getSmall())
				.build();
        
    	return dto;
    }
}
