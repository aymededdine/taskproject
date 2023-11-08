package com.adeem.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.WeekDay;
import com.adeem.task.repository.DayTaskRepository;
import com.adeem.task.repository.TaskRepository;
import com.adeem.task.repository.WeekDayRepository;
import com.adeem.task.repository.WeekTableRepository;

import lombok.Data;

@Service
@Data
public class StatisticsService {

	@Autowired
	DayTaskRepository dayTaskRepository;
	@Autowired
	WeekDayRepository weekDayRepository;
	@Autowired
	WeekTableRepository weekTableRepository;
	@Autowired
	TaskRepository taskRepository;

	Map<String, Double> numiricStatistics = new HashMap<>();
	Map<String, SortedSet<Long>> statisticsOfOrder = new HashMap<>();

	public void weeklyPlansStatistics() {

		numiricStatistics.put("totalWeeks", totalWeeks());
		numiricStatistics.put("taskCompletionRate", taskCompletionRate());
		numiricStatistics.put("taskCompletionRateByWeek", null);
		numiricStatistics.put("totalDays", totalDays());
		numiricStatistics.put("restDays", null);
		numiricStatistics.put("totalTasks", totalTasks());
		numiricStatistics.put("submittedTasks", submittedTasks());
		numiricStatistics.put("withdrawedTasks", withdrawnTasks());
		numiricStatistics.put("averageTasksPerDay", 0.0);
		numiricStatistics.put("averageTasksPerWeek", 0.0);

		statisticsOfOrder.put("activeWeeks", null);
		statisticsOfOrder.put("activeDays", null);

	}
	
	public Double totalTasks() {
		return (double) dayTaskRepository.count();
	}
	
	public Double totalDays() {
		return (double) weekDayRepository.count();
	}
	
	public Double averageTasksPerDay() {
		return totalTasks() / totalDays();
	}
	
	public Double averageTasksPerWeek() {
		return totalTasks() / totalWeeks();
	}

	public Double totalWeeks() {
		return (double) weekTableRepository.count();
	}

	public Double submittedTasks() {
		
		return (double) dayTaskRepository.countByStatus(DayTask.Status.DONE);

	}
	
	public Double withdrawnTasks() {
		
		return (double) dayTaskRepository.countByStatus(DayTask.Status.WITHDRAWN);
		
	}

	public Double taskCompletionRate() {

		var totalTasks = dayTaskRepository.count();
		var doneTasks = dayTaskRepository.countByStatus(DayTask.Status.DONE);
		if (totalTasks == 0)
			return 0.0;

		return ((double) doneTasks / totalTasks) * 100;

	}

	public Double taskCompletionRateByWeek(long weekId) {


		var numberOfTasksByWeek = 0;
		var numberOfTaskscompletedByWeek = 0;
		List<WeekDay> weekDays = weekDayRepository.findByWeekTable(
				weekTableRepository.findById(weekId).orElseThrow(() -> new IllegalArgumentException("Week not found")));

		for (WeekDay weekDay : weekDays) {
			numberOfTasksByWeek += dayTaskRepository.countByWeekDay(weekDay);
			numberOfTaskscompletedByWeek += dayTaskRepository.countByWeekDayAndStatus(weekDay, DayTask.Status.DONE);
		}

		if (numberOfTasksByWeek == 0)
			return 0.0;

		return ((double) numberOfTaskscompletedByWeek / numberOfTasksByWeek) * 100;

	}

}
