package edu.pnu.ReqDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerFormDTO {
	private String meberId;
	private String content;
	private String questionId;
}
