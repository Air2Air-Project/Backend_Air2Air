package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import edu.pnu.DTO.QuestionSimpleDTO;
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
}
