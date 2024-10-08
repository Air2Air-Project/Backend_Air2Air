package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.DTO.MemberDTO;
import edu.pnu.DTO.MyPageDTO;
import edu.pnu.DTO.RegionDTO;
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
		if(memberRepository.findByPhoneNumber(member.getPhoneNumber()).isPresent()) {			
			System.out.println("이미 존재하는 전화번호 입니다.");
			return null;
		}
		
		
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

	public boolean checkEmail(Member member) {
		Optional<Member> mem = memberRepository.findByEmail(member.getEmail());
		boolean checkEmail = mem.isPresent();	// 있으면 true, 없으면 false
		
		if(checkEmail) {			
			boolean checkDeleted = mem.get().isIsdeleted();
			if(checkDeleted)
				return false;
			else
				return true;
		}
		
		return false;
	}

	public boolean checkUsername(Member member) {
		Optional<Member> mem = memberRepository.findByUsername(member.getUsername());
		boolean checkUsername = mem.isPresent();	// 있으면 true, 없으면 false
		
		if(checkUsername) {			
			boolean checkDeleted = mem.get().isIsdeleted();
			if(checkDeleted)
				return false;
			else
				return true;
		}
		
		return false;
	}
	
	public String findId(Member member) {
		Member mem = memberRepository.findByPhoneNumber(member.getPhoneNumber()).orElse(null);

		if (mem == null)
			return "찾을 수 없는 회원입니다";
		else {
			return mem.getEmail();
		}
	}

	public boolean findPassword(Member member) {
		Member mem = memberRepository.findByEmailAndPhoneNumber(member.getEmail(), member.getPhoneNumber()).orElse(null);

		if (mem == null)
			return false;
		else
			return true;
	}
	
	public boolean changePassword(Member member) {
		Member mem = memberRepository.findByEmailAndPhoneNumber(member.getEmail(), member.getPhoneNumber()).orElse(null);
		if (mem == null)
			return false;
		
		mem.setPassword(encoder.encode(member.getPassword()));
		memberRepository.save(mem);
		return true;
	}

	public MyPageDTO getMemberInfo(String memberId) {
		Member mem = memberRepository.findById(Long.parseLong(memberId)).orElse(null);

		if (mem == null)
			return null;
		else {
			RegionDTO regionDTO = RegionDTO.builder()
					.large(mem.getRegion().getLarge())
					.middle(mem.getRegion().getMiddle())
					.small(mem.getRegion().getSmall())
					.build();

			MyPageDTO mypageDTO = MyPageDTO.builder()
					.username(mem.getUsername())
					.email(mem.getEmail())
					.phoneNumber(mem.getPhoneNumber())
					.region(regionDTO).build();
			
			return mypageDTO;
		}

	}

	public MemberDTO updateMemberInfo(Member member) {
		Member mem = memberRepository.findByEmail(member.getEmail()).orElse(null);
		if(mem == null)
			return null;
		
		Region prevRegion = member.getRegion();
		Region region = regionRepository.findByLargeAndMiddleAndSmall(prevRegion.getLarge(), prevRegion.getMiddle(),
				prevRegion.getSmall()).orElse(null);

		if (region == null)
			return null;
		else {
			mem.setUsername(member.getUsername());
			mem.setPhoneNumber(member.getPhoneNumber());
			mem.setRegion(region);
			mem.setPassword(encoder.encode(member.getPassword()));
			mem.setEmail(member.getEmail());
			memberRepository.save(mem);
			
			MemberDTO memberDTO = MemberDTO.builder()
						.username(mem.getUsername())
						.memberId(mem.getMemberId().toString())
						.role(mem.getRole())
						.stationName(mem.getRegion().getStationName())
						.build();

			return memberDTO;
		}
	}

	public Member deleteMember(String memberId) {
		Member mem = memberRepository.findById(Long.parseLong(memberId)).orElse(null);

		if (mem == null)
			return null;
		else {
			mem.setUsername("알 수 없음");
			mem.setIsdeleted(true);
			memberRepository.save(mem);
			return mem;
		}
	}

	

	
	
}
