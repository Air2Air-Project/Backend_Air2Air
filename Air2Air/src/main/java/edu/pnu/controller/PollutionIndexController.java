package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.PollutionIndexService;

@RestController
public class PollutionIndexController {
	@Autowired
	private PollutionIndexService pollutionIndexService;
	
	@GetMapping("/pollutionIndex")
	private ResponseEntity<?> getPollutionIndex(@RequestParam(required = true, defaultValue = "창원시")String large, 
			@RequestParam(required = true, defaultValue = "마산합포구") String middle, 
			@RequestParam(required = true, defaultValue = "진동면 삼진의거대로 621") String small){
		System.out.println("지역선택 호출");
		return ResponseEntity.ok(pollutionIndexService.getPollutionIndex(large, middle, small));
	}
	
	@GetMapping("/pollutionIndex/{stationName}")
	private ResponseEntity<?> getPollutionIndex(@PathVariable(required = true)String stationName) throws Exception{
		System.out.println("stationName 호출");
		return ResponseEntity.ok(pollutionIndexService.getPredictData(stationName));
	}
}
