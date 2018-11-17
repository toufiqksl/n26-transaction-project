package com.n26.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.entity.StatisticsResultResponse;
import com.n26.service.StatisticsService;

@RestController
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	@GetMapping("/statistics")
	public ResponseEntity<?> getStatistics() {
		statisticsService.getStatistics();
		ModelMapper modelMapper = new ModelMapper();
		StatisticsResultResponse response = modelMapper.map(statisticsService.getStatistics(), StatisticsResultResponse.class);
		String sum = String.format("%.2f", statisticsService.getStatistics().getSum());
		String avg = String.format("%.2f", statisticsService.getStatistics().getAvg());
		String max = String.format("%.2f", statisticsService.getStatistics().getMax());
		String min = String.format("%.2f", statisticsService.getStatistics().getMin());
		response.setSum(String.valueOf(sum));
		response.setAvg(String.valueOf(avg));
		response.setMax(String.valueOf(max));
		response.setMin(String.valueOf(min));
		return new ResponseEntity<StatisticsResultResponse>(response, HttpStatus.OK);
	}
}
