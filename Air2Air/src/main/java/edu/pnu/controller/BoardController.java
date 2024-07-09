package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.pnu.ReqDTO.AnswerFormDTO;
import edu.pnu.ReqDTO.QuestionFormDTO;
import edu.pnu.ResDTO.QuestionSimpleDTO;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/boardlist")
	public ResponseEntity<?> getBoardList(){
		List<QuestionSimpleDTO> questionList = boardService.getBoardList();
		
		return ResponseEntity.ok(questionList);
	}
	
	@GetMapping("/boardlist/search")
	public ResponseEntity<?> searchBoardList( @RequestParam(required = false) String questionType,
            @RequestParam(required = false, defaultValue = "titleContent") String searchType,
            @RequestParam(required = false) String keyword){
		List<QuestionSimpleDTO> questionList = boardService.searchBoardList(questionType, searchType, keyword);
		
		return ResponseEntity.ok(questionList);
	}
	
	@PostMapping("/question/register")
	public ResponseEntity<?> addQuestion(@RequestBody QuestionFormDTO question){
		boolean result = boardService.addQuestion(question);
		
		if(result)
			return ResponseEntity.ok("등록 성공");
		else
			return ResponseEntity.badRequest().body("등록 실패");
	}
	
	@DeleteMapping("/question/delete")
	public ResponseEntity<?> deleteQuestion(@RequestParam String questionId, @RequestParam String memberId){
		boolean result = boardService.deleteQuestion(questionId, memberId);
		
		if(result)
			return ResponseEntity.ok("삭제 성공");
		else
			return ResponseEntity.badRequest().body("삭제 실패");
	}
	
	@PostMapping("/answer/register")
	public ResponseEntity<?> addQuestion(@RequestBody AnswerFormDTO answer){
		boolean result = boardService.addAnswer(answer);
		
		if(result)
			return ResponseEntity.ok("등록 성공");
		else
			return ResponseEntity.badRequest().body("등록 실패");
	}
	
	@DeleteMapping("/answer/delete")
	public ResponseEntity<?> deleteAnswer(@RequestParam String answerId, @RequestParam String memberId){
		boolean result = boardService.deleteAnswer(answerId, memberId);
		
		if(result)
			return ResponseEntity.ok("삭제 성공");
		else
			return ResponseEntity.badRequest().body("삭제 실패");
	}
}
