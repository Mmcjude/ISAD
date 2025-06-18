package com.example.demo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.model.Project;
import com.example.demo.repo.IProjectRepo;

@Component
public class StringToProjectConverter implements Converter<String, Project> {
	@Autowired
    private IProjectRepo projectRepo;
	
	@Override
	public Project convert(String source) {
		if (source == null || source.isEmpty()) {
            return null;
        }
        long id = Long.parseLong(source);
        return projectRepo.findById(id).orElse(null);
	}
	

}
