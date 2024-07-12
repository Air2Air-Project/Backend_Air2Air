package edu.pnu.ResDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyPageDTO {
	private String email;
	private String username;
	private String phoneNumber;
	private RegionDTO region;
}
