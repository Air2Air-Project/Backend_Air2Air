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
	
	@Test
	public void addAnswer() {
		Member member = memberRepo.findById(2L).get();
		QuestionBoard question = questionRepo.findById(102L).get();
		
		 String contentWithNewlines = "안녕하세요, 관리자님. 문의 남겨주신 내용에 대해 답변드립니다.\\r\\n"
		            + "\\r\\n"
		            + "관리자페이지 [개별디자인 > 디자인 관련 기능 설정 > 상품 프로모션 기능 설정] 페이지의\\r\\n"
		            + "베스트 상품 50 메뉴에서 베스트 형식 선택 - 일간(3일) 베스트로 설정되어 있는데요.\\r\\n"
		            + "일간(3일) 베스트의 경우 금일 날짜를 제외하고 3일전 일간 베스트 상품으로 노출하게 됩니다.\r\n"
		            + "\r\n"
		            + "예를 들어서 6월 25일의 경우 해당 일자를 제외하고 3일전인 6월 22일 기준으로 노출하는데\r\n"
		            + "관리자페이지 [주문관리 > 통계 > 판매상품 월별 순위] 페이지에서\r\n"
		            + "조회기간 6월 22일 ~ 6월 22일 선택 후 상품 노출 선택 메뉴에서 [베스트 50조건]으로 체크 후 검색하시면\r\n"
		            + "베스트 상품 50 페이지에서 노출되는 순서로 확인 가능하오니 이 점 참고해 주시기 바랍니다.\r\n"
		            + "\r\n"
		            + "좋은 하루 되세요.";
		
		AnswerBoard answer = AnswerBoard.builder()
				.content(contentWithNewlines)
				.member(member)
				.questionBoard(question)
				.build();
		
		answerRepo.save(answer);
	}
	
//	@Test
	public void getQuestionArticle() {
		QuestionBoard question = questionRepo.findById(3L).get();
		
		System.out.println(question);
	}
}
