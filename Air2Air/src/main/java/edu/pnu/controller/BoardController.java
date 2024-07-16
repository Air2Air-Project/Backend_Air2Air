package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.DTO.QuestionDTO;
import edu.pnu.DTO.QuestionSimpleDTO;
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
	
	@GetMapping("/boardlist/paging")
	public ResponseEntity<?> getBoardListPaging(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size);
        Page<QuestionSimpleDTO> questionPage = boardService.getPagedQuestions(pageable);
        return ResponseEntity.ok(questionPage);
	}
	
	@GetMapping("/boardlist/search/paging")
	public ResponseEntity<?> searchBoardListPaging(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String questionType,
            @RequestParam(required = false, defaultValue = "titleContent") String searchType,
            @RequestParam(required = false) String keyword){
		Pageable pageable = PageRequest.of(page, size);
        Page<QuestionSimpleDTO> questionPage = boardService.searchPagedBoardList(pageable, questionType, searchType, keyword);
		
		return ResponseEntity.ok(questionPage);
	}	
	
	
	@GetMapping("/boardlist/search")
	public ResponseEntity<?> searchBoardList(@RequestParam(required = false) String questionType,
            @RequestParam(required = false, defaultValue = "titleContent") String searchType,
            @RequestParam(required = false) String keyword){
		List<QuestionSimpleDTO> questionList = boardService.searchBoardList(questionType, searchType, keyword);
		
		return ResponseEntity.ok(questionList);
	}	
	
	@GetMapping("/board/detail/{questionId}")
	public ResponseEntity<?> getQuestionDetail(@PathVariable Long questionId){
		QuestionDTO questionDetail = boardService.getQuestionDetail(questionId);
		
		if(questionDetail == null)
			return ResponseEntity.badRequest().body("잘못된 요청입니다");
		else
			return ResponseEntity.ok(questionDetail);
	}
}
