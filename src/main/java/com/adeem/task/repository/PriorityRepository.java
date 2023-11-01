package com.adeem.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adeem.task.entity.TaskPriority;

public interface PriorityRepository extends JpaRepository<TaskPriority, Long> {

}
