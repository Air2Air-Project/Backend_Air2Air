package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	public static QuestionType stringToEnum(String type) {
		QuestionType qtype = type.equals("관측소") ? QuestionType.STATION
				: type.equals("알람") ? QuestionType.ALERT
				: type.equals("미세먼지") ? QuestionType.DUST : QuestionType.ETC;
		
		return qtype;
	}
}
