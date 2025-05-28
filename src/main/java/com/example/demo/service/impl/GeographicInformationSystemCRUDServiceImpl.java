package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GeographicInformationSystem;
import com.example.demo.model.Habitate;
import com.example.demo.model.LocalCordinateSystem;
import com.example.demo.model.Municipality;
import com.example.demo.model.Plant;
import com.example.demo.repo.IGeographicInformationSystemRepo;
import com.example.demo.repo.IPlantRepo;
import com.example.demo.service.IGeographicInformationSystemCRUDService;

@Service
public class GeographicInformationSystemCRUDServiceImpl implements IGeographicInformationSystemCRUDService {

	@Autowired
	private IGeographicInformationSystemRepo gisRepo;
	
	@Autowired
	private IPlantRepo plantRepo;
	
	@Override
	public void insertNewGisRecord(double inputLatitude, double inputLongitude, 
			LocalCordinateSystem localLocation, Municipality municipality, Habitate habitate) throws Exception {
		if(municipality == null || habitate == null) {
			throw new Exception("Municipality and habitat fields must not be empty.");
		}
		
		if(inputLatitude < -90 || inputLatitude > 90) {
			throw new Exception("Latitude must be between -90 and 90 degrees.");
		}
		
		if(inputLongitude < -180 || inputLongitude > 180) {
			throw new Exception("Longitude must be between -180 and 180 degrees.");
		}
		
		Optional<GeographicInformationSystem> existing = gisRepo.findByLatitudeAndLongitude(inputLatitude, inputLongitude);
		if(existing.isPresent()) {
			throw new Exception("A record with this exact latitude and longitude already exists.");
		}
	    gisRepo.save(new GeographicInformationSystem(inputLatitude, inputLongitude, localLocation, municipality, habitate));
	}

	@Override
	public List<GeographicInformationSystem> retrieveAllGisRecords(){
		return (ArrayList<GeographicInformationSystem>) gisRepo.findAllByOrderByGisIdAsc();
	}

	@Override
	public GeographicInformationSystem retrieveGisRecordById(long id) throws Exception {
		 if (id <= 0) {
			 throw new Exception("Invalid ID: ID must be a positive number.");
	        }

	        if (!gisRepo.existsById(id)) {
	        	throw new Exception("No GIS record found with ID " + id + ".");
	        }

	        return gisRepo.findById(id).get();
	}

	@Override
	public void updateGisRecordById(long id, double inputLatitude, double inputLongitude,
			LocalCordinateSystem localLocation, Municipality municipality, Habitate habitate) throws Exception {
		if(municipality == null || habitate == null) {
			throw new Exception("Municipality and habitat fields must not be empty.");
		}
		
		if(inputLatitude < -90 || inputLatitude > 90) {
			throw new Exception("Latitude must be between -90 and 90 degrees.");
		}
		
		if(inputLongitude < -180 || inputLongitude > 180) {
			throw new Exception("Longitude must be between -180 and 180 degrees.");
		}
		
		GeographicInformationSystem foundGis = retrieveGisRecordById(id);
		
		Optional<GeographicInformationSystem> existing = gisRepo.findByLatitudeAndLongitude(inputLatitude, inputLongitude);
	    if (existing.isPresent() && existing.get().getGisId() != id) {
	    	throw new Exception("Another GIS record with the same latitude and longitude already exists.");
	    }
	    
	    foundGis.setLatitude(inputLatitude);
	    foundGis.setLongitude(inputLongitude);
	    foundGis.setHabitate(habitate);
	    if (localLocation != null) {
	         foundGis.setLocalLocation(localLocation);
	     } else {
	         foundGis.setLocalLocation(null);
	     }
	    foundGis.setMunicipality(municipality);
	    gisRepo.save(foundGis);		
	}

	@Override
	public void deleteGisRecordById(long id) throws Exception {
		GeographicInformationSystem gis = retrieveGisRecordById(id);
		
		for (Plant plant : gis.getPlant()) {
	        plant.setGis(null);
	        plantRepo.save(plant);
	    }
		gisRepo.delete(gis);
	}

	@Override
	public List<GeographicInformationSystem> getGisWithoutLocalCoordinates() {
		return (ArrayList<GeographicInformationSystem>) gisRepo.findGisWithoutLocalCoordinates();
	}

	@Override
	public List<GeographicInformationSystem> getGisWithLocalCoordinates() {
		return (ArrayList<GeographicInformationSystem>) gisRepo.findGisWithLocalCoordinates();
	}

	@Override
	public List<GeographicInformationSystem> getGisWithAvailableLocalCoordinates() {
		return (ArrayList<GeographicInformationSystem>) gisRepo.findGisWithAvailableLocalCoordinates();
	}

}
