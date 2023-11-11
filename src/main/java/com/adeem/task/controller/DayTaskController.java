package com.adeem.task.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adeem.task.config.DateConverter;
import com.adeem.task.entity.DayTask;
import com.adeem.task.entity.Task;
import com.adeem.task.entity.User;
import com.adeem.task.service.DayTaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/day-tasks")
public class DayTaskController {
	


    @Autowired
    private DayTaskService dayTaskService;

    @GetMapping
    public String listAllTasks(Model model) {
        List<DayTask> tasks = dayTaskService.listAll();
        
        model.addAttribute("tasks", tasks);
        return "task-list"; 
    }

    @GetMapping("/task/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        Optional<DayTask> task = dayTaskService.findById(id);
        task.ifPresent(value -> model.addAttribute("task", value));
        return "task-details"; 
    }
    
    @GetMapping("/task-form")
    public String taskForm() {
    
    	return "task-form";
    }
    
    
    @PostMapping("/add")
	public String insert(@ModelAttribute DayTask task, @AuthenticationPrincipal User user) {
//		if (errors.hasErrors()) {
//	        return "redirect:/tasks";
//			}
    	dayTaskService.insert(task, user);
		log.info("Task saved successfully");
        return "redirect:/tasks";
	}
    
    
    @PostMapping("/update")
    public String update(DayTask task) {
    	dayTaskService.update(task);
		log.info("Task updated successfully");
        return "redirect:/tasks";
    }
    
    @PostMapping("/submit/{id}")
    public ResponseEntity<?> submit(@PathVariable Long id) {
    	DayTask dayTask = dayTaskService.submit(id);
    	if(dayTask != null)
		return new ResponseEntity<>("Done Saving", HttpStatus.OK);
		return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/withdraw/{id}")
    public ResponseEntity<?> withdraw(@PathVariable Long id) {
    	DayTask dayTask = dayTaskService.withdraw(id);
    	if(dayTask != null)
    		return new ResponseEntity<>("Done withdrawing", HttpStatus.OK);
		return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> delete(@PathVariable Long id) {
    	//Long id = task.getId();
    	
    	return ResponseEntity.ok(dayTaskService.delete(id));
    }
    
    @ModelAttribute(name = "dayTask")
    public DayTask dayTask() {
    	return new DayTask();
    }
    
    

}
