package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.APIService;

@RestController
public class APIController {
	@Autowired
	private APIService apiService;
	
	@GetMapping("/getAirInfo")
	public ResponseEntity<?> getAirAPI(@RequestParam String stationName) throws Exception{
		return ResponseEntity.ok(apiService.getAirAPI(stationName));
	}
}
