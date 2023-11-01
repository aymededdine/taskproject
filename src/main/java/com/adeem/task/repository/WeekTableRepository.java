package com.adeem.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adeem.task.entity.Task;
import com.adeem.task.entity.WeekTable;


public interface WeekTableRepository extends JpaRepository<WeekTable, Long> {
	
	
	WeekTable findFirstByOrderByIdDesc();

}
