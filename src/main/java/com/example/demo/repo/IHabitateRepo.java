package com.example.demo.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Habitate;

public interface IHabitateRepo extends CrudRepository<Habitate, Long>{

	public abstract boolean existsByHabitateName(String inputName);

	public abstract ArrayList<Habitate> findAllByOrderByHabitateIdAsc();

	public abstract ArrayList<Habitate> findAllByOrderByHabitateNameAsc();

}
