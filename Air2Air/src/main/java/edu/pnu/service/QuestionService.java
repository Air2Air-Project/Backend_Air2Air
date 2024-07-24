package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.DTO.QuestionDTO;
import edu.pnu.DTO.QuestionFormDTO;
import edu.pnu.domain.Member;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.QuestionBoardRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class QuestionService {
	@Autowired
	QuestionBoardRepository questionRepository;
	@Autowired
	MemberRepository memberRepository;

	public boolean addQuestion(QuestionBoard question) {
		Member member = memberRepository.findById(question.getMember().getMemberId()).orElse(null);

		if (member == null)
			return false;
		
		question.setMember(member);
		questionRepository.save(question);

		return true;
	}
	
	public boolean deleteQuestion(String questionId, String memberId) {
		Member member = memberRepository.findById(Long.parseLong(memberId)).orElse(null);
		if (member == null)
			return false;
		
		QuestionBoard question = questionRepository.findById(Long.parseLong(questionId)).orElse(null);
		if (question == null)
			return false;

		if(member.getRole().equals(Role.ROLE_ADMIN)) {
			questionRepository.delete(question);
			return true;
		}else {			
			if(!question.getMember().getMemberId().equals(member.getMemberId()))
				return false;
		}
			
		
		questionRepository.delete(question);

		return true;
	}


	public QuestionFormDTO getModifyQuestion(String questionId, String memberId) {
		QuestionBoard question = questionRepository.findById(Long.parseLong(questionId))
				.orElse(null);
		if(question == null)
			return null;
		
		Member member = memberRepository.findById(question.getMember().getMemberId()).orElse(null);
		if(member == null || !member.getMemberId().equals(Long.parseLong(memberId)))
			return null;
		
		QuestionFormDTO questionForm = QuestionFormDTO.convertToDTO(question);
		
		return questionForm;
	}

	public boolean updateQuestion(QuestionBoard question) {
		Member member = memberRepository.findById(question.getMember().getMemberId()).orElse(null);
		if(member == null)
			return false;
		
		QuestionBoard questionBoard = questionRepository.findById(question.getSeq()).orElse(null);
		if(questionBoard == null || 
				!questionBoard.getMember().getMemberId().equals(question.getMember().getMemberId()))
			return false;
		
		questionBoard.updateQuestion(question);
		questionRepository.save(questionBoard);
		
		return true;
	}
}
