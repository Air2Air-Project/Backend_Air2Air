package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.QuestionBoard;
import edu.pnu.domain.QuestionType;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long> {
	@Query("SELECT q FROM QuestionBoard q WHERE " + "(:questionType IS NULL OR q.questionType = :questionType) AND "
			+ "((:searchType = 'title' AND (:searchKeyword IS NULL OR q.title LIKE %:searchKeyword%)) OR "
			+ "(:searchType = 'content' AND (:searchKeyword IS NULL OR q.content LIKE %:searchKeyword%)) OR "
			+ "(:searchType = 'titleContent' AND (:searchKeyword IS NULL OR (q.title LIKE %:searchKeyword% OR q.content LIKE %:searchKeyword%))) OR "
			+ "(:searchType = 'seq' AND (:searchKeyword IS NULL OR CAST(q.seq AS string) LIKE %:searchKeyword%)))")
	List<QuestionBoard> searchQuestions(@Param("questionType") QuestionType questionType,
			@Param("searchType") String searchType, @Param("searchKeyword") String searchKeyword);
	
	Page<QuestionBoard> findAll(Pageable pageable);
	
	@Query("SELECT q FROM QuestionBoard q WHERE " +
            "(:questionType IS NULL OR q.questionType = :questionType) AND " +
            "((:searchType = 'title' AND (:searchKeyword IS NULL OR q.title LIKE %:searchKeyword%)) OR " +
            "(:searchType = 'content' AND (:searchKeyword IS NULL OR q.content LIKE %:searchKeyword%)) OR " +
            "(:searchType = 'titleContent' AND (:searchKeyword IS NULL OR (q.title LIKE %:searchKeyword% OR q.content LIKE %:searchKeyword%))) OR " +
            "(:searchType = 'seq' AND (:searchKeyword IS NULL OR CAST(q.seq AS string) LIKE %:searchKeyword%)))")
    Page<QuestionBoard> searchQuestions(@Param("questionType") QuestionType questionType,
                                        @Param("searchType") String searchType,
                                        @Param("searchKeyword") String searchKeyword,
                                        Pageable pageable);
}
