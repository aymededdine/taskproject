package com.adeem.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adeem.task.entity.Task;
import com.adeem.task.entity.User;
import com.adeem.task.entity.WeekTable;


public interface WeekTableRepository extends JpaRepository<WeekTable, Long> {
	
	long countByUser(User user);
	
	
	WeekTable findFirstByUserOrderByIdDesc(User user);

	List<WeekTable> findAllByUser(User user);

}
