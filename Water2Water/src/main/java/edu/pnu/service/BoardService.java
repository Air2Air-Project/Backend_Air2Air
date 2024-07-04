package edu.pnu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import edu.pnu.DTO.QuestionSimpleDTO;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.persistence.AnswerBoardRepository;
import edu.pnu.persistence.QuestionBoardRepository;

@Service
public class BoardService {
	@Autowired
	QuestionBoardRepository questionRepository;
	@Autowired
	AnswerBoardRepository answerRepository;
	
	public List<QuestionSimpleDTO> getBoardList() {
		List<QuestionBoard> questionList = questionRepository.findAll(Sort.by(Direction.DESC, "seq"));
		List<QuestionSimpleDTO> questionDTOList = questionList.stream().map(QuestionSimpleDTO::convertToDTO)
				.collect(Collectors.toList());
		return questionDTOList;
	}
}
