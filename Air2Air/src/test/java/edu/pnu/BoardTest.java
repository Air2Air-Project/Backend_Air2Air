package edu.pnu;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.AnswerBoard;
import edu.pnu.domain.Member;
import edu.pnu.domain.QuestionBoard;
import edu.pnu.domain.QuestionType;
import edu.pnu.persistence.AnswerBoardRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.QuestionBoardRepository;

@SpringBootTest
public class BoardTest {
	@Autowired
	public AnswerBoardRepository answerRepo;
	@Autowired
	public QuestionBoardRepository questionRepo;
	@Autowired
	public MemberRepository memberRepo;
	
//	@Test
	public void addQuestion() {
		Random random = new Random();
		
		for(int i=1; i<=100; i++) {		
			Long id = (long) ((i % 5) + 1);
			Member member = memberRepo.findById(id).get();
			// 랜덤하게 questionType 선택
	        QuestionType randomQuestionType = QuestionType.values()[random.nextInt(QuestionType.values().length)];
			QuestionBoard question = QuestionBoard.builder()
					.title("문의사항있습니다" + i)
					.content("문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구")
					.member(member)
					.questionType(randomQuestionType)
					.build();
			
			questionRepo.save(question);
		}
	}
	
//	@Test
	public void addAnswer() {
		Member member = memberRepo.findById(2L).get();
		QuestionBoard question = questionRepo.findById(3L).get();
		
		AnswerBoard answer = AnswerBoard.builder()
				.content("답변입니다. 그 이유는 어쩌구 저쩌구")
				.member(member)
				.questionBoard(question)
				.build();
		
		answerRepo.save(answer);
	}
	
	@Test
	public void getQuestionArticle() {
		QuestionBoard question = questionRepo.findById(3L).get();
		
		System.out.println(question);
	}
}
