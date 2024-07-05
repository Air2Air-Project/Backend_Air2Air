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
		Member member = memberRepository.findByUsername(question.getUsername()).orElse(null);

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
		Member member = memberRepository.findByUsername(answer.getUsername()).orElse(null);
		if (member == null)
			return false;
		
		QuestionBoard question = questionRepository.findById(Long.parseLong(answer.getQuestionId())).get();
		if (question == null)
			return false;
		
		AnswerBoard answerBoard = AnswerBoard.builder()
								.content(answer.getContent())
								.member(member)
								.questionBoard(question)
								.build();
								
		answerRepository.save(answerBoard);

		return true;
	}
}
