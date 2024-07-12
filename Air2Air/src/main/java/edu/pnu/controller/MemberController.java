package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.ResDTO.MemberDTO;
import edu.pnu.ResDTO.MyPageDTO;
import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@RestController
public class MemberController {
	@Autowired
	MemberService memberService;
	
	@PostMapping("/signUp")
	public ResponseEntity<?> addMember(@RequestBody Member member){
		Member result = memberService.addMember(member);
		
		if(result == null)
			return ResponseEntity.badRequest().body("회원가입 실패");
		else
			return ResponseEntity.ok("회원가입 성공");
		
	}
	
	@PostMapping("/checkEmail")
	public ResponseEntity<?> checkEmail(@RequestBody Member member){
		boolean result = memberService.checkEmail(member);
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/checkUsername")
	public ResponseEntity<?> checkUsername(@RequestBody Member member){
		boolean result = memberService.checkUsername(member);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/user/profile/{memberId}")
	public ResponseEntity<?> getMemberInfo(@PathVariable String memberId){
		MyPageDTO result = memberService.getMemberInfo(memberId);
		if(result == null)
			return ResponseEntity.badRequest().body("잘못된 요청입니다.");
		else
			return ResponseEntity.ok(result);
	}
	
	@PutMapping("/user/profile")
	public ResponseEntity<?> updateMemberInfo(@RequestBody Member Member){
		MemberDTO result = memberService.updateMemberInfo(Member);
		if(result == null)
			return ResponseEntity.badRequest().body("잘못된 요청입니다.");
		else
			return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/user/profile/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable String memberId) {
		Member result = memberService.deleteMember(memberId);
		if(result == null)
			return ResponseEntity.badRequest().body("잘못된 요청입니다.");
		else
			return ResponseEntity.ok("삭제 되었습니다.");
	}
}
