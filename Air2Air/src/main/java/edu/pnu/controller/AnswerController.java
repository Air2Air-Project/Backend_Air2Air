package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import edu.pnu.ReqDTO.AnswerFormDTO;
import edu.pnu.service.AnswerService;

@Controller
public class AnswerController {
	@Autowired
	AnswerService answerService;
	
	@PostMapping("/answer/register")
	public ResponseEntity<?> addQuestion(@RequestBody AnswerFormDTO answer){
		boolean result = answerService.addAnswer(answer);
		
		if(result)
			return ResponseEntity.ok("등록 성공");
		else
			return ResponseEntity.badRequest().body("등록 실패");
	}
	
	@DeleteMapping("/answer/delete")
	public ResponseEntity<?> deleteAnswer(@RequestParam String answerId, @RequestParam String memberId){
		boolean result = answerService.deleteAnswer(answerId, memberId);
		
		if(result)
			return ResponseEntity.ok("삭제 성공");
		else
			return ResponseEntity.badRequest().body("삭제 실패");
	}
}
