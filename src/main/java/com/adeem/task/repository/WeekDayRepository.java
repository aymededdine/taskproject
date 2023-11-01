package com.adeem.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adeem.task.entity.WeekDay;
import com.adeem.task.entity.WeekTable;


public interface WeekDayRepository extends JpaRepository<WeekDay, Long> {

	List<WeekDay> findByWeekTable(WeekTable weekTable);
	
}
