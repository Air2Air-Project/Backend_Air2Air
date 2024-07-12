package edu.pnu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import edu.pnu.ResDTO.QuestionSimpleDTO;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.domain.QuestionType;
import edu.pnu.persistence.QuestionBoardRepository;

@Service
public class BoardService {
	@Autowired
	QuestionBoardRepository questionRepository;

	public List<QuestionSimpleDTO> getBoardList() {
		List<QuestionBoard> questionList = questionRepository.findAll(Sort.by(Direction.DESC, "seq"));
		List<QuestionSimpleDTO> questionDTOList = questionList.stream().map(QuestionSimpleDTO::convertToDTO)
				.collect(Collectors.toList());
		return questionDTOList;
	}

	public List<QuestionSimpleDTO> searchBoardList(String questionType, String searchType, String keyword) {
		QuestionType qType = null;
		if(!questionType.equals("문의유형"))
			qType = QuestionBoard.stringToEnum(questionType);
		
//		System.out.println(searchType);
//		System.out.println(qType);
//		System.out.println(questionType);
//		System.out.println(keyword);
		
		List<QuestionBoard> questionList = questionRepository.searchQuestions(qType, searchType, keyword);
		List<QuestionSimpleDTO> questionDTOList = questionList.stream().map(QuestionSimpleDTO::convertToDTO)
				.collect(Collectors.toList());
		return questionDTOList;
	}
}
