package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.ResDTO.QuestionSimpleDTO;
import edu.pnu.service.BoardService;

@RestController
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
}
