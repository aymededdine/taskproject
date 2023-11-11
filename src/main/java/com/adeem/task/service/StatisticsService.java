package com.adeem.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.User;
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

	
	
	public Double totalTasks(User user) {
		return (double) dayTaskRepository.countByUser(user);
	}
	
	public Double totalDays(User user) {
		return (double) weekDayRepository.countByUser(user);
	}
	
	public Double averageTasksPerDay(User user) {
		return totalTasks(user) / totalDays(user);
	}
	
	public Double averageTasksPerWeek(User user) {
		return totalTasks(user) / totalWeeks(user);
	}

	public Double totalWeeks(User user) {
		return (double) weekTableRepository.countByUser(user);
	}

	public Double submittedTasks(User user) {
		
		return (double) dayTaskRepository.countByUserAndStatus(user, DayTask.Status.DONE);

	}
	
	public Double withdrawnTasks(User user) {
		
		return (double) dayTaskRepository.countByUserAndStatus(user, DayTask.Status.WITHDRAWN);
		
	}

	public Double taskCompletionRate(User user) {

		var totalTasks = dayTaskRepository.countByUser(user);
		var doneTasks = dayTaskRepository.countByUserAndStatus(user, DayTask.Status.DONE);
		if (totalTasks == 0)
			return 0.0;

		return ((double) doneTasks / totalTasks) * 100;

	}

	public Double taskCompletionRateByWeek(long weekId, User user) {


		var numberOfTasksByWeek = 0;
		var numberOfTaskscompletedByWeek = 0;
		List<WeekDay> weekDays = weekDayRepository.findByWeekTable(
				weekTableRepository.findById(weekId).orElseThrow(() -> new IllegalArgumentException("Week not found")));

		for (WeekDay weekDay : weekDays) {
			numberOfTasksByWeek += dayTaskRepository.countByUserAndWeekDay(user, weekDay);
			numberOfTaskscompletedByWeek += dayTaskRepository.countByUserAndWeekDayAndStatus(user, weekDay, DayTask.Status.DONE);
		}

		if (numberOfTasksByWeek == 0)
			return 0.0;

		return ((double) numberOfTaskscompletedByWeek / numberOfTasksByWeek) * 100;

	}

}
