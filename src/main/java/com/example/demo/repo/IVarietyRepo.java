package com.example.demo.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Variety;

public interface IVarietyRepo extends CrudRepository<Variety, Long> {

	public abstract boolean existsByName(String inputName);

	public abstract ArrayList<Variety> findAllByOrderByVarietyIdAsc();

	public abstract ArrayList<Variety> findAllByOrderByNameAsc();

}
