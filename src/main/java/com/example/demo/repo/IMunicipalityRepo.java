package com.example.demo.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Country;
import com.example.demo.model.Municipality;

public interface IMunicipalityRepo extends JpaRepository<Municipality, Long>{

	public abstract ArrayList<Municipality> findAllByOrderByMunicipalityIdAsc();

	public abstract Municipality findByMunicipalityNameIgnoreCaseAndCountry(String trim, Country inputCountry);

	@Query("SELECT m FROM Municipality m ORDER BY m.country.countryName ASC, m.municipalityName ASC")
	public abstract List<Municipality> findAllByOrderByCountryNameAscAndMunicipalityNameAsc();
}
