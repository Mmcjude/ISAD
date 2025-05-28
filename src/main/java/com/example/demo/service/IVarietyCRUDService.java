package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Variety;

public interface IVarietyCRUDService {
	public abstract void insertNewVarietyRecord(String inputName) throws Exception;
	public abstract List<Variety> retrieveAllVarietyRecords();
	public abstract Variety retrieveVarietyRecordById(long id) throws Exception;
	public abstract void updateVarietyRecordById(long id, String inputName) throws Exception;
	public abstract void deleteVarietyRecordById(long id) throws Exception;
	
	public abstract List<Variety> retrieveAllVarietyRecordsByOrder();
}
