package com.example.demo.repo;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.GeographicInformationSystem;
import com.example.demo.model.Plant;
import com.example.demo.model.enums.PlantStatus;

public interface IPlantRepo extends CrudRepository<Plant, Long> {

	public abstract Optional<Plant> findByGisAndStatus(GeographicInformationSystem inputGis, PlantStatus planted);

	public abstract ArrayList<Plant> findAllByOrderByPlantIdAsc();

}
