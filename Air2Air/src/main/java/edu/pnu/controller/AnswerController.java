package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import edu.pnu.DTO.AnswerFormDTO;
import edu.pnu.DTO.QuestionFormDTO;
import edu.pnu.domain.AnswerBoard;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.service.AnswerService;

@Controller
public class AnswerController {
	@Autowired
	AnswerService answerService;
	
	@PostMapping("/answer/register")
	public ResponseEntity<?> addQuestion(@RequestBody AnswerBoard answer){
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
	
	@GetMapping("/answer/modify")
	public ResponseEntity<?> getModifyAnswer(@RequestParam String answerId, @RequestParam String memberId){
		AnswerFormDTO answerForm = answerService.getModifyAnswer(answerId, memberId);
		
		if(answerForm == null)
			return ResponseEntity.badRequest().body("잘못된 요청입니다");
		else
			return ResponseEntity.ok(answerForm);
	}
	
	@PutMapping("/answer/modify/update")
	public ResponseEntity<?> updateAnswer(@RequestBody AnswerBoard answer){
		boolean result = answerService.updateAnswer(answer);
		
		if(!result)
			return ResponseEntity.badRequest().body("수정 실패");
		else
			return ResponseEntity.ok("수정 성공");
	}
}
