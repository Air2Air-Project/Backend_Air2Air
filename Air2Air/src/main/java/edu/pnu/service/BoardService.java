package edu.pnu.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import edu.pnu.DTO.QuestionDTO;
import edu.pnu.DTO.QuestionSimpleDTO;
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
		if (!questionType.equals("문의유형"))
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

	public QuestionDTO getQuestionDetail(Long questionId) {
		QuestionBoard question = questionRepository.findById(questionId).orElse(null);
		QuestionDTO questionDetail = QuestionDTO.convertToDTO(question);

		return questionDetail;
	}

	public Page<QuestionSimpleDTO> getPagedQuestions(Pageable pageable) {
		Pageable sortedByCreatedDateDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by("seq").descending());
		Page<QuestionBoard> questionPage = questionRepository.findAll(sortedByCreatedDateDesc);
		int totalElements = (int) questionPage.getTotalElements();
        AtomicInteger start = new AtomicInteger(totalElements - (int) sortedByCreatedDateDesc.getOffset());
		return questionPage
				.map(questionBoard -> QuestionSimpleDTO.convertToDTO(questionBoard, start.getAndDecrement()));
	}

	public Page<QuestionSimpleDTO> searchBoardList(Pageable pageable, String questionType, String searchType,
			String keyword) {
		Pageable sortedByCreatedDateDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by("seq").descending());
		Page<QuestionBoard> questionPage = questionRepository.findAll(sortedByCreatedDateDesc);
		int totalElements = (int) questionPage.getTotalElements();
        AtomicInteger start = new AtomicInteger(totalElements - (int) sortedByCreatedDateDesc.getOffset());
		return questionPage
				.map(questionBoard -> QuestionSimpleDTO.convertToDTO(questionBoard, start.getAndDecrement()));
	}
	
	public Page<QuestionSimpleDTO> searchPagedBoardList(Pageable pageable, String questionType, String searchType, String searchKeyword) {
		QuestionType qType = null;
		if (!questionType.equals("문의유형"))
			qType = QuestionBoard.stringToEnum(questionType);
		
		Pageable sortedByCreatedDateDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by("seq").descending());
        Page<QuestionBoard> questionPage = questionRepository.searchQuestions(qType, searchType, searchKeyword, sortedByCreatedDateDesc);
        int totalElements = (int) questionPage.getTotalElements();
        AtomicInteger start = new AtomicInteger(totalElements - (int) sortedByCreatedDateDesc.getOffset());

        return questionPage
				.map(questionBoard -> QuestionSimpleDTO.convertToDTO(questionBoard, start.getAndDecrement()));
    }

}
