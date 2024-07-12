package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.ReqDTO.AnswerFormDTO;
import edu.pnu.ReqDTO.QuestionFormDTO;
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
	
	public boolean addAnswer(AnswerBoard answer) {
		Member member = memberRepository.findById(answer.getMember().getMemberId()).orElse(null);
		if (member == null)
			return false;
		
		QuestionBoard question = questionRepository.findById(answer.getQuestionBoard().getSeq()).get();
		if (question == null || question.isAnswered())
			return false;
		
		answer.setMember(member);
		answer.setQuestionBoard(question);
								
		answerRepository.save(answer);

		return true;
	}
	
	public boolean deleteAnswer(String answerId, String memberId) {
		Member member = memberRepository.findById(Long.parseLong(memberId)).orElse(null);
		if (member == null)
			return false;

		AnswerBoard answer = answerRepository.findById(Long.parseLong(answerId)).orElse(null);
		if (answer == null)
			return false;
		
		if(!answer.getMember().getMemberId().equals(member.getMemberId()))
			return false;

		answerRepository.delete(answer);

		return true;
	}

	public AnswerFormDTO getModifyAnswer(String answerId, String memberId) {
		AnswerBoard answer = answerRepository.findById(Long.parseLong(answerId))
				.orElse(null);
		if(answer == null)
			return null;
		
		Member member = memberRepository.findById(answer.getMember().getMemberId()).orElse(null);
		if(member == null || !member.getMemberId().equals(Long.parseLong(memberId)))
			return null;
		
		AnswerFormDTO questionForm = AnswerFormDTO.convertToDTO(answer);
		
		return questionForm;
	}

	public boolean updateAnswer(AnswerBoard answer) {
		Member member = memberRepository.findById(answer.getMember().getMemberId()).orElse(null);
		if(member == null)
			return false;
		
		AnswerBoard answerBoard = answerRepository.findById(answer.getSeq()).orElse(null);
		if(answerBoard == null || 
				!answerBoard.getMember().getMemberId().equals(answer.getMember().getMemberId()))
			return false;
		
		answerBoard.updateQuestion(answer);
		answerRepository.save(answerBoard);
		
		return true;
	}
}
