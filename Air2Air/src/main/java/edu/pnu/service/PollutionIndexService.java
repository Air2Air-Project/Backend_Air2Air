package edu.pnu.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.DTO.PollutionIndexDTO;
import edu.pnu.domain.PollutionIndex;
import edu.pnu.domain.Region;
import edu.pnu.persistence.PollutionIndexRepository;
import edu.pnu.persistence.RegionRepository;

@Service
public class PollutionIndexService {
	
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private PollutionIndexRepository pollutionIndexRepository;


	public PollutionIndexDTO getPollutionIndex(String large, String middle, String small) {
		Region region = regionRepository.findByLargeAndMiddleAndSmall(large, middle, small).orElse(null);
		System.out.println(region.getStationName());
		System.out.println(region.getRegionId());
		if(region == null)
			return null;
		
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // 2024-07-01로 고정하고 현재 시간을 기준으로 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        String formattedDate = dateFormat.format(date);
        
		PollutionIndex pollution = pollutionIndexRepository.findByDatetimeAndRegionRegionId(date, region.getRegionId());
		System.out.println(pollution);
		
		PollutionIndexDTO pollutionDTO = PollutionIndexDTO.convertToDTO(pollution);
		System.out.println(pollutionDTO);
		return pollutionDTO;
	}


	public PollutionIndexDTO getPredictData(String stationName) {
		Region region = regionRepository.findBystationName(stationName).orElse(null);
		if(region == null)
			return null;
		
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // 2024-07-01로 고정하고 현재 시간을 기준으로 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        String formattedDate = dateFormat.format(date);
        
		PollutionIndex pollution = pollutionIndexRepository.findByDatetimeAndRegionRegionId(date, region.getRegionId());
		PollutionIndexDTO pollutionDTO = PollutionIndexDTO.convertToDTO(pollution);
		
		return pollutionDTO;
	}

}
