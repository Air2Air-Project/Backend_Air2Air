package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.ReqDTO.QuestionFormDTO;
import edu.pnu.ResDTO.QuestionDTO;
import edu.pnu.service.QuestionService;

@RestController
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
	
	@GetMapping("/question/detail/{questionId}")
	public ResponseEntity<?> getQuestionDetail(@PathVariable Long questionId){
		QuestionDTO questionDetail = questionService.getQuestionDetail(questionId);
		
		if(questionDetail == null)
			return ResponseEntity.badRequest().body("잘못된 요청입니다");
		else
			return ResponseEntity.ok(questionDetail);
	}
}