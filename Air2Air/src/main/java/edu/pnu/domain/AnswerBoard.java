package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class AnswerBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@Builder.Default
	private Date createdDate = new Date();
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@OneToOne
	@JoinColumn(name = "question_board_id")
	@ToString.Exclude
	private QuestionBoard questionBoard;
}
