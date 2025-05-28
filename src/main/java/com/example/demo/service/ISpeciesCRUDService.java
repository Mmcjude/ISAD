package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Species;

public interface ISpeciesCRUDService {
	public abstract void insertNewSpecie(String inputName) throws Exception;
	public abstract List<Species> retrieveAllSpecies();
	public abstract Species retrieveSpeciesById(long id) throws Exception;
	public abstract void updateSpeciesById(long id, String inputName) throws Exception;
	public abstract void deleteSpeciesById(long id) throws Exception;
	
	public abstract List<Species> retrieveSpeciesOrderedByName();
}
