package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Project;

public interface IProjectCRUDService {
	public abstract void insertNewProjectRecord(int projectNr, String title, String description) throws Exception;
	public abstract List<Project> retrieveAllProjectRecords();
	public abstract Project retrieveProjectRecordById(long id) throws Exception;
	public abstract void updateProjectRecordBy(long id, int projectNr, String title, String description) throws Exception;
	public abstract void deleteProjectRecordById(long id) throws Exception;
}
