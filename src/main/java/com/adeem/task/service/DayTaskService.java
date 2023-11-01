package com.adeem.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.Task;
import com.adeem.task.entity.TaskStatus;
import com.adeem.task.entity.TaskStatus.Status;
import com.adeem.task.repository.DayTaskRepository;
import com.adeem.task.repository.PriorityRepository;
import com.adeem.task.repository.StatusRepository;

@Service
public class DayTaskService {

	@Autowired
	DayTaskRepository dayTaskRepo;
	
	@Autowired
	PriorityRepository priorityRepository;
	
	@Autowired
	StatusRepository statusRepository;

	public List<DayTask> listAll() {
		return dayTaskRepo.findAll();
	}

	public Optional<DayTask> findById(Long id) {
		return dayTaskRepo.findById(id);
	}


	public DayTask insert(DayTask taskObject) {
		var y = statusRepository.findById(taskObject.getStatus().getId()).orElseThrow(() -> new IllegalArgumentException("Status not found"));
		taskObject.setStatus(y);
		
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
		TaskStatus done = new TaskStatus();
		done.setId(2L);
		done.setStatus(Status.DONE);
		current.setStatus(done);
		return dayTaskRepo.save(current);
		
		}
	
	public DayTask withdraw(Long id) {
		DayTask current = dayTaskRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
		TaskStatus todo = new TaskStatus();
		todo.setId(1L);
		todo.setStatus(Status.DONE);
		current.setStatus(todo);
		current.setActive(false);
		return dayTaskRepo.save(current);
	}
}
