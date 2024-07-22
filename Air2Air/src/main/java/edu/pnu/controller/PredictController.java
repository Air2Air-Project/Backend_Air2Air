package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.schedule.ScheduledTasks;
import edu.pnu.service.PredictService;

@RestController
public class PredictController {
	@Autowired
	PredictService predictService;
	@Autowired
	ScheduledTasks schedule;
	
	@GetMapping("/request/predict")
	public ResponseEntity<?> getPredict() throws Exception{
		return ResponseEntity.ok(predictService.requestPredict());
		
	}
	
	@GetMapping("/request/calculate")
	public ResponseEntity<?> getCalculate() throws Exception{
		return ResponseEntity.ok(schedule.calculateIndex());
	}
	
	@GetMapping("/predict")
	public ResponseEntity<?> getPredict(@RequestParam(required = true, defaultValue = "창원시")String large, 
			@RequestParam(required = true, defaultValue = "마산합포구") String middle, 
			@RequestParam(required = true, defaultValue = "진동면 삼진의거대로 621") String small) throws Exception{
		return ResponseEntity.ok(predictService.getPredictData(large, middle, small));
	}
	
	@GetMapping("/predict/{stationName}")
	public ResponseEntity<?> getPredict(@PathVariable(required = true)String stationName) throws Exception{
		return ResponseEntity.ok(predictService.getPredictData(stationName));
	}
}
