package com.example.demo.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Samples;

public interface ISamplesRepo extends CrudRepository<Samples, Long> {

	public abstract ArrayList<Samples> findAllByOrderBySampleIdAsc();

}
