package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.DTO.AlertDTO;
import edu.pnu.domain.Alert;
import edu.pnu.domain.AlertType;
import edu.pnu.service.AlertService;

@RestController
public class AlertController {
	@Autowired
	private AlertService alertService;
	
	@PostMapping("/alert/add")
	private ResponseEntity<?> addAlert(@RequestBody Alert alert){
		boolean result = alertService.addAlert(alert);
		
		if(!result)
			return ResponseEntity.badRequest().body("알람 추가 실패");
		else
			return ResponseEntity.ok("알람 추가 성공");
	}
	
	@GetMapping("/alertAll/{alertType}")
	private ResponseEntity<?> getAllAlert(@PathVariable AlertType alertType){
		List<AlertDTO> alertList = alertService.getAllAlert(alertType);
		
		return ResponseEntity.ok(alertList);
	}
	
	@GetMapping("/alertSelect/{alertType}")
	private ResponseEntity<?> getSelectRegionAlert(@PathVariable AlertType alertType,
			@RequestParam(required = true, defaultValue = "창원시")String large, 
			@RequestParam(required = true, defaultValue = "마산합포구") String middle, 
			@RequestParam(required = true, defaultValue = "진동면 삼진의거대로 621") String small){
		
		System.out.println("요청: " + large + middle + small);
		List<AlertDTO> alertList = alertService.getSelectRegionAlert(alertType, large, middle, small);
		
		return ResponseEntity.ok(alertList);
	}
	
	@GetMapping("/alertSelect/{alertType}/{stationName}")
	private ResponseEntity<?> getRegionAlert(@PathVariable AlertType alertType,
			@PathVariable(required = true)String stationName){
		System.out.println("요청: " + stationName);
		List<AlertDTO> alertList = alertService.getRegionAlert(alertType, stationName);
		
		return ResponseEntity.ok(alertList);
	}
	
}
