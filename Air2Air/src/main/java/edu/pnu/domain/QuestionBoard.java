package edu.pnu.domain;

import java.util.Date;

import edu.pnu.DTO.QuestionFormDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QuestionBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	@Builder.Default
	private boolean isAnswered = false;
	
	@Column(nullable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@Builder.Default
	private Date createdDate = new Date();
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Builder.Default
	private QuestionType questionType = QuestionType.ETC;
	
	@OneToOne(mappedBy = "questionBoard", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@OrderBy("seq asc")
	private AnswerBoard answer;
	
	public static QuestionType stringToEnum(String type) {
		QuestionType qtype = type.equals("STATION") ? QuestionType.STATION
				: type.equals("ALERT") ? QuestionType.ALERT
				: type.equals("DUST") ? QuestionType.DUST : QuestionType.ETC;
		
		return qtype;
	}
	
	public static String enumToString(QuestionType type) {
		String qtype = type.equals(QuestionType.STATION) ? "관측소" 
				: type.equals(QuestionType.ETC) ? "기타"
				: type.equals(QuestionType.ALERT) ? "알람" : "미세먼지";
		
		return qtype;
	}
	
	public void updateQuestion(QuestionBoard question) {
		this.setTitle(question.getTitle());
		this.setContent(question.getContent());
		this.setQuestionType(question.getQuestionType());
    }
}
