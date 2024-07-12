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
	private Date createdDate;
	private AnswerDTO answer;
	
	public static QuestionDTO convertToDTO(QuestionBoard question) {
		if(question == null)
			return null;
		
		String type = QuestionBoard.enumToString(question.getQuestionType());

		AnswerDTO answerDTO = AnswerDTO.convertToDTO(question.getAnswer());
		
		QuestionDTO dto = QuestionDTO.builder()
				.seq(question.getSeq().toString())
    			.questionType(type)
    			.title(question.getTitle())
    			.content(question.getContent())
    			.writer(question.getMember().getUsername())
    			.createdDate(question.getCreatedDate())
    			.answer(answerDTO)
    			.build();
        
    	return dto;
    }
}
