package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.PredictService;

@RestController
public class PredictController {
	@Autowired
	PredictService predictService;
	
	@GetMapping("/request/predict")
	public ResponseEntity<?> getPredict() throws Exception{
		return ResponseEntity.ok(predictService.requestPredict());
	}
}
