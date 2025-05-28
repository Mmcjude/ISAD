package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Habitate;

public interface IHabitateCRUDService {
	public abstract void insertNewHabitateRecord(String inputName) throws Exception;
	public abstract List<Habitate> retrieveAllHabitateRecords();
	public abstract Habitate retrieveHabitateById(long id) throws Exception;
	public abstract void updateHabitateRecordById(long id, String inputName) throws Exception;
	public abstract void deleteHabitateRecordById(long id) throws Exception;
	
	public abstract List<Habitate> retrieveAllHabitateOrderedByName();
}
