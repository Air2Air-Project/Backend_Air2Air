package edu.pnu.ResDTO;

import java.util.Date;

import edu.pnu.domain.QuestionBoard;
import edu.pnu.domain.QuestionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionSimpleDTO {
	private Long seq;
	private String questionType;
	private String title;
	private String writer;
	private Date createdDate;
	
	public static QuestionSimpleDTO convertToDTO(QuestionBoard question) {
		String type = question.getQuestionType().equals(QuestionType.VALVE) ? "개도율" 
				: question.getQuestionType().equals(QuestionType.ETC) ? "기타"
				: question.getQuestionType().toString();
    	
		QuestionSimpleDTO dto = QuestionSimpleDTO.builder()
    			.seq(question.getSeq())
    			.questionType(type)
    			.title(question.getTitle())
    			.writer(question.getMember().getUsername())
    			.createdDate(question.getCreatedDate())
    			.build();
        
    	return dto;
    }
}
