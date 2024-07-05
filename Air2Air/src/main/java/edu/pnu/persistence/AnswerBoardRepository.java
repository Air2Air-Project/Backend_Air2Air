package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.AnswerBoard;

public interface AnswerBoardRepository extends JpaRepository<AnswerBoard, Long> {

}
