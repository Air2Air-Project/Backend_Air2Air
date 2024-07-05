package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class MemberTest {
	@Autowired
	public MemberRepository memberRepo;
	
//	@Test
	public void addMember() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		Member mem = Member.builder()
				.email("test@gmail.com")
				.password(encoder.encode("1234"))
				.username("test")
				.build();
		
		memberRepo.save(mem);
	}
	
	@Test
	public void findByEmail() {		
		Member mem = memberRepo.findByEmail("test@gmail.com").get();
		System.out.println(mem);
	}
}
