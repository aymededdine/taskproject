package com.adeem.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adeem.task.entity.TaskStatus;

public interface StatusRepository extends JpaRepository<TaskStatus, Long> {
	
//	@Query(value = "SELECT * FROM status WHERE status = ?1", nativeQuery = true)
//    List<TaskStatus> findByStatus(String status);

}
