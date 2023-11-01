package com.adeem.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.Task;


public interface DayTaskRepository extends JpaRepository<DayTask, Long> {
	
}
