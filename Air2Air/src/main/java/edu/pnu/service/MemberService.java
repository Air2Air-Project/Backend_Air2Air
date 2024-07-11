package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.Region;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.RegionRepository;

@Service
public class MemberService {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	PasswordEncoder encoder;
	public Member addMember(Member member) {
		Region regiRegion = member.getRegion();
		Region region = regionRepository.findByLargeAndMiddleAndSmall(
											regiRegion.getLarge(), 
											regiRegion.getMiddle(), 
											regiRegion.getSmall())
											.orElse(null);
		if(region == null)
			return null;
		
		member.setRegion(region);
		member.setPassword(encoder.encode(member.getPassword()));
		return memberRepository.save(member);
	}
}
