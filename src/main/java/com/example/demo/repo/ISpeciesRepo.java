package com.example.demo.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Species;


public interface ISpeciesRepo extends CrudRepository<com.example.demo.model.Species, Long> {

	public abstract boolean existsByName(String trim);

	public abstract ArrayList<Species> findAllByOrderBySpeciesIdAsc();

	public abstract ArrayList<Species> findAllByOrderByNameAsc();

}
