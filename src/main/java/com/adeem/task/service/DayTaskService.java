package com.adeem.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.User;
import com.adeem.task.repository.DayTaskRepository;


@Service
public class DayTaskService {

	@Autowired
	DayTaskRepository dayTaskRepo;
	

	

	public List<DayTask> listAll() {
		return dayTaskRepo.findAll();
	}

	public Optional<DayTask> findById(Long id) {
		return dayTaskRepo.findById(id);
	}


	public DayTask insert(DayTask taskObject, User user) {
		var y = taskObject.getStatus();
		taskObject.setStatus(y);
		taskObject.setUser(user);
		
		return dayTaskRepo.save(taskObject);
	}

	public DayTask update(DayTask task) {
		// Use Optional's orElseThrow to provide a custom exception message.
		DayTask current = dayTaskRepo.findById(task.getId())
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));

		// Update the fields individually.
		current.setName(task.getName());
		current.setStatus(task.getStatus());

		return dayTaskRepo.save(current);
	}

	public boolean delete(Long taskId) {
		if (dayTaskRepo.existsById(taskId)) {
			dayTaskRepo.deleteById(taskId);
			
			return true;
		}
		return false;
	}

	
	public DayTask submit(Long id) {
		DayTask current = dayTaskRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));
		current.setStatus(DayTask.Status.DONE);
		return dayTaskRepo.save(current);
		
		}
	
	public DayTask withdraw(Long id) {
		DayTask current = dayTaskRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
		current.setStatus(DayTask.Status.WITHDRAWN);
		return dayTaskRepo.save(current);
	}
}
