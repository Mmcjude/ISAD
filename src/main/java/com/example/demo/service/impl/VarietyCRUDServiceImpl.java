package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Plant;
import com.example.demo.model.Variety;
import com.example.demo.repo.IPlantRepo;
import com.example.demo.repo.IVarietyRepo;
import com.example.demo.service.IVarietyCRUDService;

@Service
public class VarietyCRUDServiceImpl implements IVarietyCRUDService {
	
	@Autowired
	private IVarietyRepo varRepo;
	
	@Autowired
	private IPlantRepo plantRepo;
	
	@Override
	public void insertNewVarietyRecord(String inputName) throws Exception {
		if(inputName == null || inputName.trim().isEmpty()) {
			throw new Exception("Variety name cannot be null or empty.");
		}
		
		if(!inputName.trim().matches("^[A-Za-zĀ-Žā-ž'&\\-\\. ]{3,70}$")) {
			throw new Exception("Variety name must be 3 to 70 characters long and can contain letters, Latvian letters, apostrophes, ampersands, hyphens, dots, or spaces.");
		}
		
		if(varRepo.existsByName(inputName.trim())){
			throw new Exception("Variety with this name is already in the system.");
		}
		
		varRepo.save(new Variety(inputName.trim()));
	}

	@Override
	public List<Variety> retrieveAllVarietyRecords(){
		return (ArrayList<Variety>) varRepo.findAllByOrderByVarietyIdAsc();
	}

	@Override
	public Variety retrieveVarietyRecordById(long id) throws Exception {
		if(id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
		}
		
		if(!varRepo.existsById(id)) {
			throw new Exception("Variety table record with id " + id + " doesn't exist.");
		}
		
		return varRepo.findById(id).get();
	}

	@Override
	public void updateVarietyRecordById(long id, String inputName) throws Exception {
		if(inputName == null || inputName.trim().isEmpty()) {
			throw new Exception("Variety name can not be null or empty.");
		}
		
		if(!inputName.trim().matches("^[A-Za-zĀ-Žā-ž'&\\-\\. ]{3,70}$")) {
			throw new Exception("Variety name must be 3 to 70 characters long and can contain letters, Latvian letters, apostrophes, ampersands, hyphens, dots, or spaces.");
		}
		
		Variety foundVariety = retrieveVarietyRecordById(id);
		
		if (varRepo.existsByName(inputName.trim()) && !foundVariety.getName().equals(inputName.trim())) {
			throw new Exception("Variety with this name is already in the system.");
	    }
		
		foundVariety.setName(inputName.trim());
		varRepo.save(foundVariety);
	}

	@Override
	public void deleteVarietyRecordById(long id) throws Exception {
		Variety variety = retrieveVarietyRecordById(id);

        for (Plant plant : variety.getPlants()) {
        	plant.setVariety(null);
        	plantRepo.save(plant);
        }
		varRepo.delete(variety);
	}

	@Override
	public List<Variety> retrieveAllVarietyRecordsByOrder() {
		return (ArrayList<Variety>) varRepo.findAllByOrderByNameAsc();
	}

}
