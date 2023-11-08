package com.adeem.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.DayTask.Status;
import com.adeem.task.entity.WeekDay;


public interface DayTaskRepository extends JpaRepository<DayTask, Long> {
	
	Long countByStatus(Status status);
	
	
	Double countByWeekDay(WeekDay weekDay);
	
	Double countByWeekDayAndStatus(WeekDay weekDay, Status status);
		
}
