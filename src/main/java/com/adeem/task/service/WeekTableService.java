package com.adeem.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.WeekDay;
import com.adeem.task.entity.WeekTable;
import com.adeem.task.repository.WeekDayRepository;
import com.adeem.task.repository.WeekTableRepository;

@Service
public class WeekTableService {
	
	@Autowired
	private WeekDayRepository weekDayRepository;
	

    private final WeekTableRepository weekTableRepository;

    @Autowired
    public WeekTableService(WeekTableRepository weekTableRepository) {
        this.weekTableRepository = weekTableRepository;
    }

    // Create a new WeekTable
    public WeekTable insert() {
    	WeekTable weekTable = new WeekTable();
        return weekTableRepository.save(weekTable);
    }
    
    public WeekTable insert(WeekTable weekTable) {
        return weekTableRepository.save(weekTable);
    }

    // Retrieve a WeekTable by its ID
    public WeekTable findById(Long id) {
        return weekTableRepository.findById(id).orElse(null);
    }

    // Retrieve all WeekTables
    public List<WeekTable> findAll() {
        return weekTableRepository.findAll();
    }
    
    public WeekTable findLast() {
    	return weekTableRepository.findFirstByOrderByIdDesc();
    }

    // Update an existing WeekTable
    public WeekTable update(WeekTable weekTable) {
        return weekTableRepository.save(weekTable);
    }

    // Delete a WeekTable by its ID
    public void delete(Long id) {
        weekTableRepository.deleteById(id);
    }

	public ResponseEntity<?> submitWeek(Long id) {
		
		WeekTable weekTable = weekTableRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Status not found"));
		List<WeekDay> weekDays = weekDayRepository.findByWeekTable(weekTable);
		
		for(WeekDay weekDay : weekDays) {
			for(DayTask dayTask : weekDay.getDayTasks()) {
				if(dayTask.isActive() && dayTask.getStatus().getId() == 1)
					return new ResponseEntity<>("Cannot Submit a non-completed week", HttpStatus.EXPECTATION_FAILED);
			}
		}
		
		weekTable.setSubmitted(true);
	    WeekTable savedWeekTable = weekTableRepository.save(weekTable);
	    
	    if (savedWeekTable != null) {
	        return new ResponseEntity<>("Week submitted successfully", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Failed to submit week", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
		
	}
}
