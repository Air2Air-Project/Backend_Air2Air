package edu.pnu.DTO;

import java.util.Date;

import edu.pnu.domain.AnswerBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
	private String seq;
	private String content;
	private String writer;
	private Date createdDate;
	
	public static AnswerDTO convertToDTO(AnswerBoard answer) {
		if(answer == null)
			return null;
		
		AnswerDTO dto = AnswerDTO.builder()
				.seq(answer.getSeq().toString())
				.content(answer.getContent())
				.writer(answer.getMember().getUsername())
				.createdDate(answer.getCreatedDate())
				.build();
		
    	return dto;
    }
}
