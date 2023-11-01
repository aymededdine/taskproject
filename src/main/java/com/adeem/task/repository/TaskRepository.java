package com.adeem.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adeem.task.entity.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {
	
	boolean deleteGoalById(Long id);
	Task findByName(String name);



}
