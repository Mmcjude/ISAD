package com.example.demo.service;

import java.util.List;
import com.example.demo.model.LocalCordinateSystem;

public interface ILocalCordinateSystemCRUDService {
	public abstract void insertNewLocalLocationRecord(int inputFieldNumber, 
			int inputRow, int inputNumber) throws Exception;
	public abstract List<LocalCordinateSystem> retrieveAllLocalLocationRecords();
	public abstract LocalCordinateSystem retrieveLocalLocationRecordById(long id) throws Exception;
	public abstract void updateLocalLocationRecordBy(long id, int inputFieldNumber, 
			int inputRow, int inputNumber) throws Exception;
	public abstract void deleteLocalLocationRecordById(long id) throws Exception;

	public abstract List<LocalCordinateSystem> getAvailableLocalCoordinatesOrdered();
	public abstract List<LocalCordinateSystem> getAvailableLocalCoordinatesOrderedIncludingCurrent(Long id) throws Exception;
}
