package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.AirPredict;
import edu.pnu.domain.PredictRequest;
import edu.pnu.persistence.AirPredictRepository;
import edu.pnu.persistence.PredictRequestRespository;

@SpringBootTest
public class PredictTest {
	@Autowired
	PredictRequestRespository predictResquestRepo;
	@Autowired
	AirPredictRepository airPredictRepo;
	
	@Test
	public void getTable() {
		List<PredictRequest> reqList = predictResquestRepo.findAll();
		List<AirPredict> airList = airPredictRepo.findAll();
		
		for(PredictRequest req: reqList) {
			System.out.println(req);
		}
		
		for(AirPredict air: airList) {
			System.out.println(air);
		}
	}
	
}
