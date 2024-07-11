package edu.pnu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
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
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_id", nullable = false)
	private Long memberId;
	
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식이 아닙니다.")
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Enumerated(EnumType.STRING)
	@Builder.Default
	@Column(nullable = false)
	private Role role = Role.ROLE_USER;
	
	@ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
	
	@Column(name="phone_number", nullable = false)
	private String phoneNumber;
}
