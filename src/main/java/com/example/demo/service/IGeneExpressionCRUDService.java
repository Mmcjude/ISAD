package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.GeneExpression;
import com.example.demo.model.Project;
import com.example.demo.model.Samples;
import com.example.demo.model.enums.HasSample;

public interface IGeneExpressionCRUDService {
	public abstract void insertNewGeneExpression(String name, LocalDate date, HasSample hasSample, double expressionFoldNumber,
			String geneSequence, Samples sample, ArrayList<Project> projects) throws Exception;
	public abstract List<GeneExpression> retrieveAllGeneExpressions();
	public abstract GeneExpression retrieveGeneExpressionById(long id) throws Exception;
	public abstract void updateGeneExpressionById(long id, String name, LocalDate date, HasSample hasSample, double expressionFoldNumber,
			String geneSequence, Samples sample, ArrayList<Project> projects) throws Exception;
	public abstract void deleteGeneExpressionById(long id) throws Exception;
}
