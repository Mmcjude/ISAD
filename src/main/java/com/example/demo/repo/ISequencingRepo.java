package com.example.demo.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Sequencing;

public interface ISequencingRepo extends CrudRepository<Sequencing, Long> {

	public abstract ArrayList<Sequencing> findAllByOrderBySequencingIdAsc();

	public abstract boolean existsByName(String trim);

	public abstract boolean existsByNameAndSequencingIdNot(String trim, long id);

}
