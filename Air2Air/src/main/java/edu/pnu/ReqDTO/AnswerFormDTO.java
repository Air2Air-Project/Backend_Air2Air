package edu.pnu.ReqDTO;

import edu.pnu.domain.AnswerBoard;
import edu.pnu.domain.QuestionBoard;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerFormDTO {
	private String seq;
	private String memberId;
	private String content;
//	private String questionId;
	
	public static AnswerFormDTO convertToDTO(AnswerBoard answer) {
		if(answer == null)
			return null;
		
		AnswerFormDTO dto = AnswerFormDTO.builder()
				.seq(answer.getSeq().toString())
				.content(answer.getContent())
				.memberId(answer.getMember().getMemberId().toString())
				.build();
		
    	return dto;
    }
}
