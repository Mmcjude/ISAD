package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GeographicInformationSystem;
import com.example.demo.model.Habitate;
import com.example.demo.repo.IGeographicInformationSystemRepo;
import com.example.demo.repo.IHabitateRepo;
import com.example.demo.service.IHabitateCRUDService;

@Service
public class HabitateCRUDServiceImpl implements IHabitateCRUDService {
	@Autowired
	private IHabitateRepo habitateRepo;
	
	@Autowired
	private IGeographicInformationSystemRepo gisRepo;
	
	@Override
	public void insertNewHabitateRecord(String inputName) throws Exception {
		if(inputName == null || inputName.trim().isEmpty()) {
			throw new Exception("Habitate name cannot be null or empty.");
		}
		
		if(!inputName.matches("^[A-Za-zĀ-Žā-ž'\\- ]{2,50}$")) {
			throw new Exception("Habitate name must be 2 to 50 characters long and contain only letters, apostrophes, hyphens, or spaces.");
		}
		
		if(habitateRepo.existsByHabitateName(inputName.trim())){
			throw new Exception("Habitate with this name is already in the system.");
		}

        Habitate newHabitate = new Habitate(inputName.trim());
        habitateRepo.save(newHabitate);
	}

	@Override
	public List<Habitate> retrieveAllHabitateRecords() {
		return (ArrayList<Habitate>) habitateRepo.findAllByOrderByHabitateIdAsc();
	}

	@Override
	public Habitate retrieveHabitateById(long id) throws Exception {
		if (id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
        }

        if (!habitateRepo.existsById(id)) {
        	throw new Exception("Local Coordinate System record with ID " + id + " does not exist.");
        }

        return habitateRepo.findById(id).get();
	}

	@Override
	public void updateHabitateRecordById(long id, String inputName) throws Exception {
		if(inputName == null || inputName.trim().isEmpty()) {
			throw new Exception("Input habitate name can't be null or empty.");
		}
		
        if(!inputName.matches("^[A-Za-zĀ-Žā-ž'\\- ]{2,50}$")) {
        	throw new Exception("Habitate name must be 2 to 50 characters long and contain only letters, apostrophes, hyphens, or spaces.");
		}
        
        Habitate foundHabitate = retrieveHabitateById(id);
        
		if(habitateRepo.existsByHabitateName(inputName.trim()) && !foundHabitate.getHabitateName().equals(inputName.trim())){
			throw new Exception("Habitate with this name is already in the system.");
		}

        foundHabitate.setHabitateName(inputName.trim());
        habitateRepo.save(foundHabitate);
		
	}

	@Override
	public void deleteHabitateRecordById(long id) throws Exception {
		Habitate habitate = retrieveHabitateById(id);

        for (GeographicInformationSystem gis : habitate.getGisList()) {
        	gis.setMunicipality(null);
        	gisRepo.save(gis);
        }
        habitateRepo.delete(habitate);
	}

	@Override
	public List<Habitate> retrieveAllHabitateOrderedByName() {
		return (ArrayList<Habitate>) habitateRepo.findAllByOrderByHabitateNameAsc();
	}
}
