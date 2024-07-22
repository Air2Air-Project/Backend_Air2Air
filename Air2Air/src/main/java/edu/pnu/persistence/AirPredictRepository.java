package edu.pnu.persistence;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.AirPredict;

public interface AirPredictRepository extends JpaRepository<AirPredict, Long> {
	Optional<AirPredict> findByRegionRegionIdAndPredictTime(Long regionId, Date predictedTime);
}
