package edu.pnu.DTO;

import edu.pnu.domain.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDTO {
	private String username;
	private Role role;
}
