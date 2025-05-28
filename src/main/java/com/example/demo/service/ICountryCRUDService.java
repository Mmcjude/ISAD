package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Country;

public interface ICountryCRUDService {
	public abstract void insertNewCountry(String inputName) throws Exception;
	public abstract List<Country> retrieveAllCountries();
	public abstract Country retrieveCountryById(long id) throws Exception;
	public abstract void updateCountryById(long id, String inputName) throws Exception;
	public abstract void deleteCountryById(long id) throws Exception;
	
	public abstract List<Country> retrieveCountriesOrderedByName();
}
