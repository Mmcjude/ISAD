package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Country;
import com.example.demo.model.GeographicInformationSystem;
import com.example.demo.model.Municipality;
import com.example.demo.repo.IGeographicInformationSystemRepo;
import com.example.demo.repo.IMunicipalityRepo;
import com.example.demo.service.IMunicipalityCRUDService;

@Service
public class MunicipalityCRUDServiceImpl implements IMunicipalityCRUDService{
		@Autowired
	    private IMunicipalityRepo municipalityRepo;

		@Autowired
		private IGeographicInformationSystemRepo gisRepo;
		
	    @Override
	    public void insertNewMunicipality(String inputName, Country inputCountry) throws Exception {
	        if (inputName == null || inputName.trim().isEmpty() || inputCountry == null) {
	        	throw new Exception("Municipality name and country must not be null or empty.");
	        }
	        
	        if (!inputName.trim().matches("^[A-Za-zĀ-Žā-ž'&\\- ]{2,50}$")) {
	        	throw new Exception("Municipality name must be 2 to 50 characters long and contain only letters, apostrophes, ampersands, hyphens, or spaces.");
	        }
	        
	        Municipality existingMunicipality = municipalityRepo.findByMunicipalityNameIgnoreCaseAndCountry(inputName.trim(), inputCountry);
	        if (existingMunicipality != null) {
	            throw new Exception("A municipality with this name already exists for the selected country.");
	        }

	        Municipality newMunicipality = new Municipality(inputName.trim(), inputCountry);
	        municipalityRepo.save(newMunicipality);
	    }

	    @Override
	    public List<Municipality> retrieveAllMunicipalities(){
	        return (ArrayList<Municipality>) municipalityRepo.findAllByOrderByMunicipalityIdAsc();
	    }
	   
	    @Override
	    public Municipality retrieveMunicipalityById(long id) throws Exception {
	        if (id <= 0) {
	        	throw new Exception("Invalid ID: ID must be a positive number.");
	        }

	        if (!municipalityRepo.existsById(id)) {
	        	throw new Exception("Municipality record with ID " + id + " does not exist.");
	        }

	        return municipalityRepo.findById(id).get();
	    }

	    @Override
	    public void updateMunicipalityById(long id, String inputName, Country inputCountry) throws Exception {
	    	if (inputName == null || inputName.trim().isEmpty() || inputCountry == null) {
	        	throw new Exception("Municipality name and country must not be null or empty.");
	        }
	        
	        if (!inputName.trim().matches("^[A-Za-zĀ-Žā-ž'&\\- ]{2,50}$")) {
	        	throw new Exception("Municipality name must be 2 to 50 characters long and contain only letters, apostrophes, ampersands, hyphens, or spaces.");
	        }
	        
	        Municipality existingMunicipality = municipalityRepo.findByMunicipalityNameIgnoreCaseAndCountry(inputName.trim(), inputCountry);
	        if (existingMunicipality != null && existingMunicipality.getMunicipalityId() != id) {
	        	throw new Exception("A municipality with this name already exists for the selected country.");
	        }

	        Municipality foundMunicipality = retrieveMunicipalityById(id);
	        foundMunicipality.setMunicipalityName(inputName.trim());
	        foundMunicipality.setCountry(inputCountry);
	        municipalityRepo.save(foundMunicipality);
	    }

	    @Override
	    public void deleteMunicipalityById(long id) throws Exception {
	    	Municipality municipality = retrieveMunicipalityById(id);

	        for (GeographicInformationSystem gis : municipality.getGisList()) {
	        	gis.setMunicipality(null);
	        	gisRepo.save(gis);
	        }
	        municipalityRepo.delete(municipality);
	    }

		@Override
		public List<Municipality> retrieveAllMunicipalitiesOrderedByCountryByName() {
			List<Municipality> municipalities = municipalityRepo.findAllByOrderByCountryNameAscAndMunicipalityNameAsc();
		    
		    Municipality dobeleMunicipality = null;

		    for (Municipality m : municipalities) {
		        String countryName = m.getCountry().getCountryName();
		        String municipalityName = m.getMunicipalityName();
		        
		        if ((countryName.equalsIgnoreCase("Latvia") || countryName.equalsIgnoreCase("Latvija")) &&
		            municipalityName.toLowerCase().contains("dobele")) {
		            dobeleMunicipality = m;
		            break;
		        }
		    }
		    
		    if (dobeleMunicipality != null) {
		        municipalities.remove(dobeleMunicipality);
		        municipalities.add(0, dobeleMunicipality);
		    }
		    
		    return municipalities;
		}
}
