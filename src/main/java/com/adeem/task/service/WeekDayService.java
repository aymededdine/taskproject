package com.adeem.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.adeem.task.entity.User;
import com.adeem.task.entity.WeekDay;
import com.adeem.task.repository.WeekDayRepository;

@Service
public class WeekDayService {

	@Autowired
	WeekDayRepository weekDayRepository;
	

	


	public List<WeekDay> listAll() {
		return weekDayRepository.findAll();
	}

	public Optional<WeekDay> findById(Long id) {
		return weekDayRepository.findById(id);
	}


	public WeekDay insert(WeekDay weekDay, User user) {
		weekDay.setUser(user);
		return weekDayRepository.save(weekDay);
	}

	public WeekDay update(WeekDay weekDay) {
		// Use Optional's orElseThrow to provide a custom exception message.
		WeekDay current = weekDayRepository.findById(weekDay.getId())
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));

		// Update the fields individually.
		current.setDayOfWeek(weekDay.getDayOfWeek());
		current.setDayTasks(weekDay.getDayTasks());

		return weekDayRepository.save(current);
	}

	public boolean delete(Long taskId) {
		if (weekDayRepository.existsById(taskId)) {
			weekDayRepository.deleteById(taskId);
			
			return true;
		}
		return false;
	}

	
}
