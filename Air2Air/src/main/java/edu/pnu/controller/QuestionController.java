package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.DTO.QuestionDTO;
import edu.pnu.DTO.QuestionFormDTO;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.service.QuestionService;

@RestController
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@PostMapping("/question/register")
	public ResponseEntity<?> addQuestion(@RequestBody QuestionBoard question){
		boolean result = questionService.addQuestion(question);
		
		if(result)
			return ResponseEntity.ok("등록 성공");
		else
			return ResponseEntity.badRequest().body("등록 실패");
	}
	
	@DeleteMapping("/question/delete")
	public ResponseEntity<?> deleteQuestion(@RequestParam String questionId, @RequestParam String memberId){
		System.out.println("memberId: " + memberId);
		System.out.println("questionId: " + questionId);
		
		boolean result = questionService.deleteQuestion(questionId, memberId);
		
		
		
		if(result)
			return ResponseEntity.ok("삭제 성공");
		else
			return ResponseEntity.badRequest().body("삭제 실패");
	}
	
	
	
	@GetMapping("/question/modify")
	public ResponseEntity<?> getModifyQuestion(@RequestParam String questionId, @RequestParam String memberId){
		QuestionFormDTO questionForm = questionService.getModifyQuestion(questionId, memberId);
		
		if(questionForm == null)
			return ResponseEntity.badRequest().body("잘못된 요청입니다");
		else
			return ResponseEntity.ok(questionForm);
	}
	
	@PutMapping("/question/modify/update")
	public ResponseEntity<?> updateQuestion(@RequestBody QuestionBoard question){
		boolean result = questionService.updateQuestion(question);
		
		if(!result)
			return ResponseEntity.badRequest().body("수정 실패");
		else
			return ResponseEntity.ok("수정 성공");
	}
}
