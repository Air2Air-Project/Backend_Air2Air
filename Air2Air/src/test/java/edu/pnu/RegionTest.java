package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Region;
import edu.pnu.persistence.RegionRepository;

@SpringBootTest
public class RegionTest {

	@Autowired
	public RegionRepository regionRepo;
	
	@Test
	public void getRegion() {
		List<Region> list = regionRepo.findAll();
		
		for(Region region : list) {
			System.out.println(region);
		}
	}
	
//	@Test
	public void findByLargeMiddleSmall() {
		Region region = regionRepo.findByLargeAndMiddleAndSmall("창원시", "마산합포구", "진동면 삼진의거대로 621").orElse(null);
		
		if(region == null)
			System.out.println("없는 지역입니다");
		else
			System.out.println(region);
	}
}
