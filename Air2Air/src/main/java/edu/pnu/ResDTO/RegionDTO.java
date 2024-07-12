package edu.pnu.ResDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionDTO {
	private String large;
	private String middle;
	private String small;
}
