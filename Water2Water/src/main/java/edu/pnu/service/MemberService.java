package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	PasswordEncoder encoder;
	public Member addMember(Member member) {
		member.setPassword(encoder.encode(member.getPassword()));
		return memberRepository.save(member);
	}
}
