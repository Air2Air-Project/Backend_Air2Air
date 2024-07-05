package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@Controller
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
}
