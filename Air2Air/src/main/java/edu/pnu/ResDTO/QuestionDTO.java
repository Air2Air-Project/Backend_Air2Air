package edu.pnu.ResDTO;

import java.util.Date;

import edu.pnu.domain.QuestionBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
	private String seq;
	private String questionType;
	private String title;
	private String content;
	private String writer;
	private MemberIdDTO member;
	private Date createdDate;
	private AnswerDTO answer;
	
	public static QuestionDTO convertToDTO(QuestionBoard question) {
		if(question == null)
			return null;
		
		String type = QuestionBoard.enumToString(question.getQuestionType());

		AnswerDTO answerDTO = AnswerDTO.convertToDTO(question.getAnswer());
		MemberIdDTO memberDTO = MemberIdDTO.builder()
				.memberId(question.getMember().getMemberId().toString())
				.build();
		
		QuestionDTO dto = QuestionDTO.builder()
				.seq(question.getSeq().toString())
    			.questionType(type)
    			.title(question.getTitle())
    			.content(question.getContent())
    			.writer(question.getMember().getUsername())
    			.member(memberDTO)
    			.createdDate(question.getCreatedDate())
    			.answer(answerDTO)
    			.build();
        
    	return dto;
    }
}
