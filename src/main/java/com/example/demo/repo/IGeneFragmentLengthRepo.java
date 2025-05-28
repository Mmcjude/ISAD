package com.example.demo.repo;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.GeneFragmentLength;

public interface IGeneFragmentLengthRepo extends CrudRepository<GeneFragmentLength, Long> {

	public abstract boolean existsByName(String name);

	public abstract ArrayList<GeneFragmentLength> findAllByOrderByGeneFragmentIdAsc();

	public abstract Optional<GeneFragmentLength> findByName(String trim);


}
