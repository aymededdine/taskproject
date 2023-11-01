package com.adeem.task.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.adeem.task.entity.TaskStatus;
import com.adeem.task.repository.StatusRepository;

public class StatusConvertor implements Converter<Long, TaskStatus>  {
	
	@Autowired
	private StatusRepository statusRepository;

	@Override
	public TaskStatus convert(Long status) {
		return statusRepository.getById(status);
	}

}
