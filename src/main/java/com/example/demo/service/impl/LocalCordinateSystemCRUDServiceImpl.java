package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GeographicInformationSystem;
import com.example.demo.model.LocalCordinateSystem;
import com.example.demo.repo.IGeographicInformationSystemRepo;
import com.example.demo.repo.ILocalCordinateSystemRepo;
import com.example.demo.service.ILocalCordinateSystemCRUDService;

@Service
public class LocalCordinateSystemCRUDServiceImpl implements ILocalCordinateSystemCRUDService {
	@Autowired
	ILocalCordinateSystemRepo localRepo;
	
	@Autowired
	private IGeographicInformationSystemRepo gisRepo;

	@Override
	public void insertNewLocalLocationRecord(int inputFieldNumber, int inputRow, int inputNumber) throws Exception {
		if(inputFieldNumber < 0) {
			throw new Exception("The input field number '" + inputFieldNumber + "' is out of bounds (must be >= 1).");
		}
		
		if(inputRow < 0) {
			throw new Exception("The input row number '" + inputRow + "' is out of bounds (must be >= 1).");
		}
		
		if(inputNumber < 0) {
			throw new Exception("The input number '" + inputNumber + "' is out of bounds (must be >= 1).");
		}
		
		if(localRepo.existsByFieldNumberAndRowAndNumber(inputFieldNumber, inputRow, inputNumber)) {
		    throw new Exception("The combination of field number, row, and number must be unique. A location with these values already exists.");
		}
		
		localRepo.save(new LocalCordinateSystem(inputFieldNumber, inputRow, inputNumber));
	}

	@Override
	public List<LocalCordinateSystem> retrieveAllLocalLocationRecords(){
		return (ArrayList<LocalCordinateSystem>) localRepo.findAlByOrderByLcsIdAsc();
	}

	@Override
	public LocalCordinateSystem retrieveLocalLocationRecordById(long id) throws Exception {
		if(id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
		}
		
		if(!localRepo.existsById(id)) {
			throw new Exception("Local Coordinate System Table record with id " + id + " doesn't exist.");
		}
		
		return localRepo.findById(id).get();
	}

	@Override
	public void updateLocalLocationRecordBy(long id, int inputFieldNumber, int inputRow, int inputNumber)
			throws Exception {
		if(inputFieldNumber < 0) {
			throw new Exception("The input field number '" + inputFieldNumber + "' is out of bounds (must be >= 1).");
		}
		
		if(inputRow < 0) {
			throw new Exception("The input row number '" + inputRow + "' is out of bounds (must be >= 1).");
		}
		
		if(inputNumber < 0) {
			throw new Exception("The input number '" + inputNumber + "' is out of bounds (must be >= 1).");
		}
		
		 Optional<LocalCordinateSystem> existing = localRepo
		            .findByFieldNumberAndRowAndNumber(inputFieldNumber, inputRow, inputNumber);

		 if (existing.isPresent() && existing.get().getLcsId() != id) {
			 throw new Exception("The combination of field number, row, and number must be unique. A location with these values already exists.");
		 }
		
		LocalCordinateSystem foundLocation = retrieveLocalLocationRecordById(id);
		foundLocation.setFieldNumber(inputFieldNumber);
		foundLocation.setRow(inputRow);
		foundLocation.setNumber(inputNumber);
		localRepo.save(foundLocation);
	}

	@Override
	public void deleteLocalLocationRecordById(long id) throws Exception {
		LocalCordinateSystem location = retrieveLocalLocationRecordById(id);

		GeographicInformationSystem gis = location.getGis();
	    if (gis != null) {
	        gis.setLocalLocation(null);
	        gisRepo.save(gis);
	    }
	    localRepo.delete(location);
	}

	@Override
	public List<LocalCordinateSystem> getAvailableLocalCoordinatesOrdered() {
		return (ArrayList<LocalCordinateSystem>) localRepo.findAvailableOrdered();
	}

	@Override
	public List<LocalCordinateSystem> getAvailableLocalCoordinatesOrderedIncludingCurrent(Long id) throws Exception {
		if (id == null) {
	        return (ArrayList<LocalCordinateSystem>) localRepo.findAvailableOrdered();
	    }

		LocalCordinateSystem foundLocal = retrieveLocalLocationRecordById(id);
	    return localRepo.findAvailableIncludingCurrent(foundLocal.getLcsId());
	}
}
