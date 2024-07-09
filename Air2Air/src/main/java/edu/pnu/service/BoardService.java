package edu.pnu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import edu.pnu.ReqDTO.AnswerFormDTO;
import edu.pnu.ReqDTO.QuestionFormDTO;
import edu.pnu.ResDTO.QuestionSimpleDTO;
import edu.pnu.domain.AnswerBoard;
import edu.pnu.domain.Member;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.domain.QuestionType;
import edu.pnu.persistence.AnswerBoardRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.QuestionBoardRepository;

@Service
public class BoardService {
	@Autowired
	QuestionBoardRepository questionRepository;
	@Autowired
	AnswerBoardRepository answerRepository;
	@Autowired
	MemberRepository memberRepository;

	public List<QuestionSimpleDTO> getBoardList() {
		List<QuestionBoard> questionList = questionRepository.findAll(Sort.by(Direction.DESC, "seq"));
		List<QuestionSimpleDTO> questionDTOList = questionList.stream().map(QuestionSimpleDTO::convertToDTO)
				.collect(Collectors.toList());
		return questionDTOList;
	}

	public boolean addQuestion(QuestionFormDTO question) {
		Member member = memberRepository.findById(Long.parseLong(question.getMemberId())).orElse(null);

		if (member == null)
			return false;

		QuestionBoard questionBoard = QuestionBoard.builder()
									.member(member).title(question.getTitle())
									.content(question.getContent())
									.questionType(QuestionBoard.stringToEnum(question.getType()))
									.build();

		questionRepository.save(questionBoard);

		return true;
	}

	public boolean addAnswer(AnswerFormDTO answer) {
		Member member = memberRepository.findById(Long.parseLong(answer.getMeberId())).orElse(null);
		if (member == null)
			return false;
		
		QuestionBoard question = questionRepository.findById(Long.parseLong(answer.getQuestionId())).get();
		if (question == null || question.isAnswered())
			return false;
		
		AnswerBoard answerBoard = AnswerBoard.builder()
								.content(answer.getContent())
								.member(member)
								.questionBoard(question)
								.build();
								
		answerRepository.save(answerBoard);

		return true;
	}

	public boolean deleteQuestion(String questionId, String memberId) {
		Member member = memberRepository.findById(Long.parseLong(memberId)).orElse(null);
		if (member == null)
			return false;

		QuestionBoard question = questionRepository.findById(Long.parseLong(questionId)).orElse(null);
		if (question == null)
			return false;

		if(!question.getMember().equals(member))
			return false;
		
		questionRepository.delete(question);

		return true;
	}
	
	public boolean deleteAnswer(String answerId, String memberId) {
		Member member = memberRepository.findById(Long.parseLong(memberId)).orElse(null);
		if (member == null)
			return false;

		AnswerBoard answer = answerRepository.findById(Long.parseLong(answerId)).orElse(null);
		if (answer == null)
			return false;

		answerRepository.delete(answer);

		return true;
	}

	public List<QuestionSimpleDTO> searchBoardList( String questionType, String searchType, String keyword) {
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
