package com.example.demo.service;

import java.util.List;

import com.example.demo.model.GeographicInformationSystem;
import com.example.demo.model.Plant;
import com.example.demo.model.Variety;
import com.example.demo.model.enums.PlantOrigin;
import com.example.demo.model.enums.PlantStatus;


public interface IPlantCRUDService {
	public abstract void insertNewPlantRecord(Variety inputVariety, PlantOrigin inputOrigin, 
			PlantStatus inputStatus, String inputNotes, GeographicInformationSystem inputGis) throws Exception;
	public abstract List<Plant> retrieveAllPlantRecords();
	public abstract Plant retrievePlantRecordById(long id) throws Exception;
	public abstract void updatePlantRecordBy(long id, Variety inputVaruiety, PlantOrigin inputOrigin, 
			PlantStatus inputStatus, String inputNotes, GeographicInformationSystem inputGis) throws Exception;
	public abstract void deletePlantRecordById(long id) throws Exception;
}
