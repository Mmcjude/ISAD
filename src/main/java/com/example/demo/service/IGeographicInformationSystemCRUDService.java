package com.example.demo.service;

import java.util.List;

import com.example.demo.model.GeographicInformationSystem;
import com.example.demo.model.Habitate;
import com.example.demo.model.LocalCordinateSystem;
import com.example.demo.model.Municipality;

public interface IGeographicInformationSystemCRUDService {
	public abstract void insertNewGisRecord(double inputLatitude, double inputLongitude, 
			LocalCordinateSystem localLocation, Municipality municipality, Habitate habitate) throws Exception;
	public abstract List<GeographicInformationSystem> retrieveAllGisRecords();
	public abstract GeographicInformationSystem retrieveGisRecordById(long id) throws Exception;
	public abstract void updateGisRecordById(long id, double inputLatitude, double inputLongitude, 
			LocalCordinateSystem localLocation, Municipality municipality, Habitate habitate) throws Exception;
	public abstract void deleteGisRecordById(long id) throws Exception;

	public abstract List<GeographicInformationSystem> getGisWithoutLocalCoordinates();
	public abstract List<GeographicInformationSystem> getGisWithLocalCoordinates();
	public abstract List<GeographicInformationSystem> getGisWithAvailableLocalCoordinates();
}
