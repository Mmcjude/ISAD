package com.example.demo.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Country;

public interface ICountryRepo extends JpaRepository<Country, Long>{

	public abstract boolean existsByCountryName(String inputName);

	public abstract ArrayList<Country> findAllByOrderByCountryIdAsc();

	public abstract List<Country> findAllByOrderByCountryNameAsc();

}
