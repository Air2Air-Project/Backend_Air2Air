package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.ReqDTO.QuestionFormDTO;
import edu.pnu.ResDTO.QuestionDTO;
import edu.pnu.domain.Member;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.QuestionBoardRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class QuestionService {
	@Autowired
	QuestionBoardRepository questionRepository;
	@Autowired
	MemberRepository memberRepository;

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

	public QuestionDTO getQuestionDetail(Long questionId) {
		QuestionBoard question = questionRepository.findById(questionId)
				.orElse(null);
		QuestionDTO questionDetail = QuestionDTO.convertToDTO(question);
		
		return questionDetail;
	}

	public QuestionFormDTO getModifyQuestion(Long questionId) {
		QuestionBoard question = questionRepository.findById(questionId)
				.orElse(null);
		
		QuestionFormDTO questionForm = QuestionFormDTO.convertToDTO(question);
		
		return questionForm;
	}
}
