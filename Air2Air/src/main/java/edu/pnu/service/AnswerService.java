package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.ReqDTO.AnswerFormDTO;
import edu.pnu.domain.AnswerBoard;
import edu.pnu.domain.Member;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.persistence.AnswerBoardRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.QuestionBoardRepository;

@Service
public class AnswerService {
	@Autowired
	QuestionBoardRepository questionRepository;
	@Autowired
	AnswerBoardRepository answerRepository;
	@Autowired
	MemberRepository memberRepository;
	
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
}
