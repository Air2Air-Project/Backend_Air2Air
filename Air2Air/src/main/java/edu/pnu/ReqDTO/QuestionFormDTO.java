package edu.pnu.ReqDTO;

import edu.pnu.domain.QuestionBoard;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionFormDTO {
	private String type;
	private String title;
	private String content;
	private String memberId;
	
	public static QuestionFormDTO convertToDTO(QuestionBoard question) {
		if(question == null)
			return null;
		
		QuestionFormDTO dto = QuestionFormDTO.builder()
				.type(QuestionBoard.enumToString(question.getQuestionType()))
				.title(question.getTitle())
				.content(question.getTitle())
				.memberId(question.getMember().getMemberId().toString())
				.build();
		
    	return dto;
    }
}
