package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Samples;
import com.example.demo.model.Species;
import com.example.demo.repo.ISamplesRepo;
import com.example.demo.repo.ISpeciesRepo;
import com.example.demo.service.ISpeciesCRUDService;

@Service
public class SpeciesCRUDServiceImpl implements ISpeciesCRUDService {
	@Autowired
	private ISpeciesRepo speciesRepo;
	
	@Autowired
	private ISamplesRepo sampleRepo;

	@Override
	public void insertNewSpecie(String inputName) throws Exception {
		if (inputName == null || inputName.trim().isEmpty()) {
			throw new Exception("Species name cannot be null or empty.");
        }
		
        if(!inputName.trim().matches("^[A-ZĀ-Ž][a-zA-Zā-žĀ-Ž\\- ]{1,49}$")) {
        	throw new Exception("Species name must start with a capital letter and can contain letters (including Latvian), spaces, or hyphens. Length must be 2–50 characters.");
		}
        
		if(speciesRepo.existsByName(inputName.trim())){
			 throw new Exception("Species with this name is already in the system.");
		}
		
        speciesRepo.save(new Species(inputName.trim()));
	}

	@Override
	public List<Species> retrieveAllSpecies() {
		return (ArrayList<Species>) speciesRepo.findAllByOrderBySpeciesIdAsc();
	}

	@Override
	public Species retrieveSpeciesById(long id) throws Exception {
		if (id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
        }

        if (!speciesRepo.existsById(id)) {
        	throw new Exception("Species table record with id " + id + " doesn't exist.");
        }
        
        return speciesRepo.findById(id).get();
	}

	@Override
	public void updateSpeciesById(long id, String inputName) throws Exception {
		if (inputName == null || inputName.trim().isEmpty()) {
			throw new Exception("Species name cannot be null or empty.");
        }
		
        if(!inputName.trim().matches("^[A-ZĀ-Ž][a-zA-Zā-žĀ-Ž\\- ]{1,49}$")) {
        	throw new Exception("Species name must start with a capital letter and can contain letters (including Latvian), spaces, or hyphens. Length must be 2–50 characters.");
		}
        
        Species foundSpecies = retrieveSpeciesById(id);

        if (speciesRepo.existsByName(inputName.trim()) && !foundSpecies.getName().equalsIgnoreCase(inputName.trim())) {
        	throw new Exception("Species with this name is already in the system.");
        }

        foundSpecies.setName(inputName.trim());
        speciesRepo.save(foundSpecies);
	}

	@Override
	public void deleteSpeciesById(long id) throws Exception {
		Species specie = retrieveSpeciesById(id);
		
		for(Samples sample : specie.getSamples()) {
			sample.setSpecie(null);
			sampleRepo.save(sample);
		}	
		speciesRepo.delete(specie);
	}

	@Override
	public List<Species> retrieveSpeciesOrderedByName() {
		return (ArrayList<Species>) speciesRepo.findAllByOrderByNameAsc();
	}

}
