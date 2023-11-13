package com.adeem.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adeem.task.entity.Task;
import com.adeem.task.entity.User;


public interface TaskRepository extends JpaRepository<Task, Long> {
	
	boolean deleteGoalById(Long id);
	Task findByName(String name);
	List<Task> findAllByUser(User user);



}
