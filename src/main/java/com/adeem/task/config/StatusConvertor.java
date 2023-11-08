package com.adeem.task.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.adeem.task.entity.TaskStatus;

public class StatusConvertor implements Converter<Long, TaskStatus>  {

	@Override
	public TaskStatus convert(Long source) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
