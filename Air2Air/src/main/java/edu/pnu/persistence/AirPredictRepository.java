package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.AirPredict;

public interface AirPredictRepository extends JpaRepository<AirPredict, Long> {

}
