package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GeographicInformationSystem;
import com.example.demo.model.Plant;
import com.example.demo.model.Samples;
import com.example.demo.model.Variety;
import com.example.demo.model.enums.PlantOrigin;
import com.example.demo.model.enums.PlantStatus;
import com.example.demo.repo.IPlantRepo;
import com.example.demo.repo.ISamplesRepo;
import com.example.demo.service.IPlantCRUDService;

@Service
public class PlantCRUDServiceImpl implements IPlantCRUDService {
	@Autowired
	private IPlantRepo plantRepo;
	
	@Autowired
	private ISamplesRepo sampleRepo;

	@Override
	public void insertNewPlantRecord(Variety inputVariety, PlantOrigin inputOrigin, PlantStatus inputStatus, 
			String inputNotes, GeographicInformationSystem inputGis) throws Exception {
		if(inputVariety == null ||inputOrigin == null || inputGis == null) {
			throw new Exception("Variety, origin, and GIS fields must not be null.");
		}
		
		if (inputOrigin == PlantOrigin.Local && inputStatus == null) {
			throw new Exception("Local plant entries must include a status.");
		}

		if (inputOrigin == PlantOrigin.External && inputStatus != null) {
			throw new Exception("External plant entries must not include a status -> Set to NULL/None value.");
		}
		
		if(inputOrigin ==  PlantOrigin.Local && inputGis.getLocalLocation() == null) {
			throw new Exception("Local plant entries must have local location coordinates defined in GIS record.");
		}
		
		if (inputOrigin == PlantOrigin.Local && inputStatus == PlantStatus.Planted) {
	        Optional<Plant> existing = plantRepo.findByGisAndStatus(inputGis, PlantStatus.Planted);
	        if (existing.isPresent()) {
	        	throw new Exception(
	        					"A planted local plant already exists at the specified coordinates. " +
	        					"Conflicting record: Plant ID " + existing.get().getPlantId() +
	        		            ", Variety: " + existing.get().getVariety().getName() + "."
	                );
	        }
	    }

		Plant newPlant = new Plant();
		newPlant.setVariety(inputVariety);
		newPlant.setOrigin(inputOrigin);

		if (inputOrigin == PlantOrigin.Local) {
			newPlant.setStatus(inputStatus);
		}

		newPlant.setAdditionalNotes(inputNotes);
		newPlant.setGis(inputGis);
		plantRepo.save(newPlant);
	}

	@Override
	public List<Plant> retrieveAllPlantRecords(){
		 return (ArrayList<Plant>) plantRepo.findAllByOrderByPlantIdAsc();
	}

	@Override
	public Plant retrievePlantRecordById(long id) throws Exception {
		if(id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
		}
		
		if(!plantRepo.existsById(id)) {
			throw new Exception("Plant table record with ID " + id + " does not exist.");
		}
		
		return plantRepo.findById(id).get();
	}

	@Override
	public void updatePlantRecordBy(long id, Variety inputVariety, PlantOrigin inputOrigin,
			PlantStatus inputStatus, String inputNotes, GeographicInformationSystem inputGis) throws Exception {
		if(inputVariety == null ||inputOrigin == null || inputGis == null) {
			throw new Exception("Variety, origin, and GIS fields must not be null.");
		}
		
		if (inputOrigin == PlantOrigin.Local && inputStatus == null) {
			throw new Exception("Local plant entries must include a status.");
		}

		if (inputOrigin == PlantOrigin.External && inputStatus != null) {
			throw new Exception("External plant entries must not include a status -> Set to NULL/None value.");
		}
		
		if(inputOrigin ==  PlantOrigin.Local && inputGis.getLocalLocation() == null) {
			throw new Exception("Local plant entries must have local location coordinates defined in GIS record.");
		}
		
		if (inputOrigin == PlantOrigin.Local && inputStatus == PlantStatus.Planted) {
		    Optional<Plant> existing = plantRepo.findByGisAndStatus(inputGis, PlantStatus.Planted);
		    
		    if (existing.isPresent() && existing.get().getPlantId() != id) {
		    	throw new Exception(
		    					"A planted local plant already exists at the specified coordinates. " +
		    					"Conflicting record: Plant ID " + existing.get().getPlantId() +
	        		            ", Variety: " + existing.get().getVariety().getName() + "."
	                );
		    }
		}
		
		Plant foundPlant = retrievePlantRecordById(id);
		foundPlant.setOrigin(inputOrigin);
		foundPlant.setVariety(inputVariety);;
		foundPlant.setStatus(inputStatus);
		foundPlant.setGis(inputGis);
		foundPlant.setAdditionalNotes(inputNotes);
		plantRepo.save(foundPlant);
	}

	@Override
	public void deletePlantRecordById(long id) throws Exception {
		Plant plant = retrievePlantRecordById(id);
		
		for (Samples sample : plant.getSamples()) {
			sample.setPlant(null);
			sampleRepo.save(sample);
		}
 	     plantRepo.delete(plant);
	}	
}
