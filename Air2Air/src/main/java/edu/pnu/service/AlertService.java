package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Alert;
import edu.pnu.domain.AlertType;
import edu.pnu.domain.Region;
import edu.pnu.persistence.AlertRepository;
import edu.pnu.persistence.RegionRepository;

@Service
public class AlertService {
	@Autowired
	private AlertRepository alertRepository;
	@Autowired
	private RegionRepository regionRepository;

	public boolean addAlert(Alert alert) {
		Region region = regionRepository.findById(alert.getRegion().getRegionId()).orElse(null);
		
		if(region == null)
			return false;
		
		alert.setRegion(region);
		alertRepository.save(alert);
		return true;
	}

	public List<Alert> getAllAlert(AlertType alertType) {
		List<Alert> alertList = alertRepository.findByAlertType(alertType);
		
		return alertList;
	}

	public List<Alert> getSelectRegionAlert(AlertType alertType, String large, String middle, String small) {
		Region region = regionRepository.findByLargeAndMiddleAndSmall(large, middle, small).orElse(null);
		if(region == null)
			return null;
		
		List<Alert> alertList = alertRepository.findByAlertTypeAndRegionRegionId(alertType, region.getRegionId());
		
		return alertList;
		
	}

	public List<Alert> getRegionAlert(AlertType alertType, String stationName) {
		Region region = regionRepository.findBystationName(stationName).orElse(null);
		if(region == null)
			return null;
		
		List<Alert> alertList = alertRepository.findByAlertTypeAndRegionRegionId(alertType, region.getRegionId());
		
		return alertList;
	}
}
