package com.example.demo.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Country;
import com.example.demo.model.Municipality;
import com.example.demo.repo.ICountryRepo;
import com.example.demo.repo.IMunicipalityRepo;
import com.example.demo.service.ICountryCRUDService;

@Service
public class CountryCRUDServiceImpl implements ICountryCRUDService{
	 	@Autowired
	    private ICountryRepo countryRepo;
	 	
	 	@Autowired
	 	private IMunicipalityRepo municipalityRepo;

	    @Override
	    public void insertNewCountry(String inputName) throws Exception {
	        if (inputName == null || inputName.trim().isEmpty()) {
	        	throw new Exception("Please enter a country name. It cannot be empty.");
	        }
	        if(!inputName.trim().matches("[A-Za-zĀ-Žā-ž -]{2,50}")) {
	        	 throw new Exception("The country name should only contain letters, spaces, or hyphens (2–50 characters).");
			}
			if(countryRepo.existsByCountryName(inputName.trim())){
				throw new Exception("This country already exists in the system.");
			}

	        Country newCountry = new Country(inputName.trim());
	        countryRepo.save(newCountry);
	    }

	    @Override
	    public List<Country> retrieveAllCountries(){
	        return (ArrayList<Country>) countryRepo.findAllByOrderByCountryIdAsc();
	    }

	    @Override
	    public Country retrieveCountryById(long id) throws Exception {
	        if (id <= 0) {
	        	 throw new Exception("Invalid ID: ID must be a positive number.");
	        }

	        if (!countryRepo.existsById(id)) {
	        	throw new Exception("No country found with ID " + id + ".");
	        }

	        return countryRepo.findById(id).get();
	    }

	    @Override
	    public void updateCountryById(long id, String inputName) throws Exception {
	        if (inputName == null ||  inputName.trim().isEmpty()) {
	        	throw new Exception("Please enter a country name. It cannot be empty.");
	        }
	        if(!inputName.trim().matches("[A-Za-zĀ-Žā-ž -]{2,50}")) {
	        	throw new Exception("The country name should contain only letters, spaces, or hyphens, and be between 2 and 50 characters long.");
			}
	        
	        Country foundCountry = retrieveCountryById(id);
	        
			if(countryRepo.existsByCountryName(inputName.trim()) && !foundCountry.getCountryName().equals(inputName.trim())){
				throw new Exception("A country with this name already exists.");
			}

	        foundCountry.setCountryName(inputName.trim());
	        countryRepo.save(foundCountry);
	    }

	    @Override
	    public void deleteCountryById(long id) throws Exception {
	    	Country country = retrieveCountryById(id);
	    	
	    	for (Municipality m : country.getMunicipalities()) {
	            m.setCountry(null);
	            municipalityRepo.save(m);
	        }
	        countryRepo.delete(country);
	    }

		@Override
		public List<Country> retrieveCountriesOrderedByName() {
			List<Country> countries = countryRepo.findAllByOrderByCountryNameAsc();
			List<Country> result = new ArrayList<>();

			Country latvia = null;
			for (Country country : countries) {
		        String name = country.getCountryName().toLowerCase();
		        if (name.contains("latvia") || name.contains("latvija")) {
		            latvia = country;
		            break;
		        }
		    }
	        
	        if (latvia != null) {
	            result.add(latvia);
	            countries.remove(latvia);
	        }
	        
	        result.addAll(countries);
	        
	        return result;
		}
}
