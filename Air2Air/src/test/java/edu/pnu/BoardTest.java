package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.AnswerBoard;
import edu.pnu.domain.Member;
import edu.pnu.domain.QuestionBoard;
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
		Member member = memberRepo.findById(1L).get();
		
		QuestionBoard question = QuestionBoard.builder()
				.title("질문입니다.")
				.content("질문입니다. 이건 왜 이렇게 되나요?")
				.member(member)
				.build();
		
		questionRepo.save(question);
	}
	
	@Test
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
}
