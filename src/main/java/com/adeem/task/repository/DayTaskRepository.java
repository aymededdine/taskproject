package com.adeem.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.DayTask.Status;
import com.adeem.task.entity.User;
import com.adeem.task.entity.WeekDay;


public interface DayTaskRepository extends JpaRepository<DayTask, Long> {
	
	long countByUser(User user);
	
	Long countByUserAndStatus(User user, Status status);
	
	
	Double countByUserAndWeekDay(User user, WeekDay weekDay);
	
	Double countByUserAndWeekDayAndStatus(User user, WeekDay weekDay, Status status);
		
}
