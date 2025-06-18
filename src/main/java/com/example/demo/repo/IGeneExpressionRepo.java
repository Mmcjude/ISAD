package com.example.demo.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.GeneExpression;

public interface IGeneExpressionRepo extends CrudRepository<GeneExpression, Long> {

	public abstract ArrayList<GeneExpression> findAllByOrderByExpressionIdAsc();

	public abstract boolean existsByExpressionNameAndExpressionIdNot(String trim, long id);

	public abstract boolean existsByExpressionName(String trim);

}
