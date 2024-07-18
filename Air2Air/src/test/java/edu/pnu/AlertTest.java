package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Alert;
import edu.pnu.domain.AlertType;
import edu.pnu.domain.Region;
import edu.pnu.persistence.AlertRepository;
import edu.pnu.persistence.RegionRepository;

@SpringBootTest
public class AlertTest {
	@Autowired
	AlertRepository alertRepo;
	@Autowired
	RegionRepository regionRepo;
	
//	@Test
//	public void addAlert() {
//		Region region = regionRepo.findById(14L).get();
//		
//		Alert alert = Alert.builder()
//				.region(region)
//				.alertType(AlertType.POLLUTION)
//				.build();
//		
//		alertRepo.save(alert);
//	}
}
