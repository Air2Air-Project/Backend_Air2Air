package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Alert;
import edu.pnu.domain.AlertType;

public interface AlertRepository  extends JpaRepository<Alert, Long>{
	List<Alert> findByAlertTypeOrderByAlertTimeDesc(AlertType alertType);
	List<Alert> findByAlertTypeAndRegionRegionId(AlertType alertType, Long regionId);
	
}
