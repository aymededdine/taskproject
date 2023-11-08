package com.adeem.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adeem.task.entity.Task;
import com.adeem.task.repository.TaskRepository;



@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepo;
	



	public List<Task> listAll() {
		return taskRepo.findAll();
	}

	public Optional<Task> findById(Long id) {
		return taskRepo.findById(id);
	}

	public Task findByName(String name) {
		return taskRepo.findByName(name);
	}

	public Task insert(Task task) {
		
		task.setPriority(task.getPriority());
		task.setStatus(task.getStatus());
		
		return taskRepo.save(task);
	}

	public Task update(Long id, String name) {
		// Use Optional's orElseThrow to provide a custom exception message.
		Task current = taskRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));

		current.setName(name);

		return taskRepo.save(current);
	}

	public boolean deleteById(Long taskId) {
		if (taskRepo.existsById(taskId)) {
			taskRepo.deleteById(taskId);
			
			return true;
		}
		return false;
	}

	public ResponseEntity<?> submit(Long id) {
		
		Task current = taskRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));

		 current.setStatus(Task.Status.DONE);
		 
		 if(taskRepo.save(current) != null)
			 return new ResponseEntity<>("Task submitted successfully", HttpStatus.OK);
		 return new ResponseEntity<>("Error while submitting", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
public ResponseEntity<?> withdraw(Long id) {
		
		Task current = taskRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));

		 current.setStatus(Task.Status.WITHDRAWN);
		 
		 if(taskRepo.save(current) != null)
			 return new ResponseEntity<>("Task withdrawn successfully", HttpStatus.OK);
		 return new ResponseEntity<>("Error while withdrawing", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
