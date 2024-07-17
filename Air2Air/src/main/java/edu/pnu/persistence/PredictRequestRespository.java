package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.PredictRequest;

public interface PredictRequestRespository extends JpaRepository<PredictRequest, Long>{

}
