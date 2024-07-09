package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import edu.pnu.ReqDTO.QuestionFormDTO;
import edu.pnu.service.QuestionService;

@Controller
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@PostMapping("/question/register")
	public ResponseEntity<?> addQuestion(@RequestBody QuestionFormDTO question){
		boolean result = questionService.addQuestion(question);
		
		if(result)
			return ResponseEntity.ok("등록 성공");
		else
			return ResponseEntity.badRequest().body("등록 실패");
	}
	
	@DeleteMapping("/question/delete")
	public ResponseEntity<?> deleteQuestion(@RequestParam String questionId, @RequestParam String memberId){
		boolean result = questionService.deleteQuestion(questionId, memberId);
		
		if(result)
			return ResponseEntity.ok("삭제 성공");
		else
			return ResponseEntity.badRequest().body("삭제 실패");
	}
}
