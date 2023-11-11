package com.adeem.task.controller;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adeem.task.entity.User;
import com.adeem.task.service.StatisticsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/statistics")
public class StatisticsContoller {

	@Autowired
	StatisticsService statisticsService;

//	@GetMapping("/test")
//	public ResponseEntity<?> test() {
//		return ResponseEntity.ok("total weeks: " + statisticsService.totalWeeks() + " task completion rate "
//				+ statisticsService.taskCompletionRate() + "task completion rate by week "
//				+ statisticsService.taskCompletionRateByWeek(2L));
//	}

	@GetMapping("")
	public String statisticsPage(Model model, @AuthenticationPrincipal User user) {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		DecimalFormat decimalFormat2 = new DecimalFormat("#");
	    model.addAttribute("totalTasks", decimalFormat2.format(statisticsService.totalTasks(user)));
		model.addAttribute("totalDays", decimalFormat2.format(statisticsService.totalDays(user)));
		model.addAttribute("averageTasksPerDay", decimalFormat.format(statisticsService.averageTasksPerDay(user)));
		model.addAttribute("averageTasksPerWeek", decimalFormat.format(statisticsService.averageTasksPerWeek(user)));
		model.addAttribute("totalWeeks", decimalFormat2.format(statisticsService.totalWeeks(user)));
		model.addAttribute("submittedTasks", decimalFormat2.format(statisticsService.submittedTasks(user)));
		model.addAttribute("withdrawnTasks", decimalFormat2.format(statisticsService.withdrawnTasks(user)));
		model.addAttribute("taskCompletionRate", decimalFormat.format(statisticsService.taskCompletionRate(user)));
		return "statistics";
	}

}
