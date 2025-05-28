package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Country;
import com.example.demo.model.Municipality;

public interface IMunicipalityCRUDService {
	public abstract void insertNewMunicipality(String inputName, Country inputCountry) throws Exception;
	public abstract List<Municipality> retrieveAllMunicipalities();
	public abstract Municipality retrieveMunicipalityById(long id) throws Exception;
	public abstract void updateMunicipalityById(long id, String inputName, Country inputCountry) throws Exception;
	public abstract void deleteMunicipalityById(long id) throws Exception;

	public abstract List<Municipality> retrieveAllMunicipalitiesOrderedByCountryByName();
}
