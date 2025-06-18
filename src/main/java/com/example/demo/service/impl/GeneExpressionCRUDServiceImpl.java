package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GeneExpression;
import com.example.demo.model.Project;
import com.example.demo.model.Samples;
import com.example.demo.model.enums.HasSample;
import com.example.demo.repo.IGeneExpressionRepo;
import com.example.demo.repo.IProjectRepo;
import com.example.demo.service.IGeneExpressionCRUDService;

@Service
public class GeneExpressionCRUDServiceImpl implements IGeneExpressionCRUDService {
	@Autowired
	private IGeneExpressionRepo expRepo;
	
	@Autowired
	private IProjectRepo projectRepo;

	@Override
	public void insertNewGeneExpression(String name, LocalDate date, HasSample hasSample, double expressionFoldNumber,
			String geneSequence, Samples sample, ArrayList<Project> projects) throws Exception {
		if(name == null || name.isBlank() || date == null || hasSample == null || 
				geneSequence == null || geneSequence.isBlank()) {
			throw new Exception("Input parameters must be provided and cannot be empty.");
		}
		
		if(name.trim().length() < 2 || name.trim().length() > 100) {
			throw new Exception("Gene Fragment Length name must be between 2 and 100 characters long.");
		}
		
		if (expressionFoldNumber < 0) {
			throw new Exception("Expression Fold Number must be a positive number greater than zero.");
		}
		
		if(!geneSequence.matches("^[ACGTNU]+$")) {
			throw new Exception("Gene sequence contains invalid characters. Only capital letters A, C, G, T, N, and U are allowed.");
		}
		
		if(expRepo.existsByExpressionName(name.trim())){
			throw new Exception("A record with the name '" + name.trim() + "' already exists. Please choose a unique name.");
		}
		
		ArrayList<Project> validatedProjects = new ArrayList<>();
		if (projects != null && !projects.isEmpty()) {
			for (Project p : projects) {
				if (p == null || !projectRepo.existsById(p.getProjectId())) {
					throw new Exception("Invalid project in the list: project does not exist or is null.");
				}
				validatedProjects.add(projectRepo.findById(p.getProjectId()).get());
			}
		}
		
		GeneExpression exp = new GeneExpression(name, date, hasSample, expressionFoldNumber, geneSequence, sample, validatedProjects);
		expRepo.save(exp);
		
		for(Project project : validatedProjects) {
			project.getExpressions().add(exp);
			projectRepo.save(project);
		}
	}

	@Override
	public List<GeneExpression> retrieveAllGeneExpressions() {
		return (ArrayList<GeneExpression>) expRepo.findAllByOrderByExpressionIdAsc();
	}

	@Override
	public GeneExpression retrieveGeneExpressionById(long id) throws Exception {
		if (id <= 0) {
	       	 throw new Exception("Invalid ID: ID must be a positive number.");
		}

		if (!expRepo.existsById(id)) {
			throw new Exception("No Gene Expression record found with ID " + id + ".");
		}
		        
		return expRepo.findById(id).get();
	}

	@Override
	public void updateGeneExpressionById(long id, String name, LocalDate date, HasSample hasSample,
			double expressionFoldNumber, String geneSequence, Samples sample, ArrayList<Project> projects)
			throws Exception {
		if(name == null || name.isBlank() || date == null || hasSample == null || 
				geneSequence == null || geneSequence.isBlank()) {
			throw new Exception("Input parameters must be provided and cannot be empty.");
		}
		
		if(name.trim().length() < 2 || name.trim().length() > 100) {
			throw new Exception("Gene Fragment Length name must be between 2 and 100 characters long.");
		}
		
		if (expressionFoldNumber < 0) {
			throw new Exception("Expression Fold Number must be a positive number greater than zero.");
		}
		
		if(!geneSequence.matches("^[ACGTNU]+$")) {
			throw new Exception("Gene sequence contains invalid characters. Only capital letters A, C, G, T, N, and U are allowed.");
		}
		
		GeneExpression found = retrieveGeneExpressionById(id);
		
		if (expRepo.existsByExpressionNameAndExpressionIdNot(name.trim(), id)) {
		    throw new Exception("A gene expression with the name '" + name.trim() + "' already exists.");
		}
		
		List<Project> validatedProjects = new ArrayList<>();
		if (projects != null && !projects.isEmpty()) {
			for (Project project : projects) {
				if (project == null || !projectRepo.existsById(project.getProjectId())) {
					throw new Exception("Invalid project in the list: project does not exist or is null.");
				}
			validatedProjects.add(projectRepo.findById(project.getProjectId()).get());
			}
	    }
		
		found.setExpressionName(name);
		found.setDate(date);
		found.setHasSample(hasSample);
		found.setExpressionFoldNumber(expressionFoldNumber);
		found.setGeneSequence(geneSequence);
		found.setSample(sample);

		for (Project oldProject : found.getProjects()) {
		    oldProject.getExpressions().remove(found);
		}
		found.getProjects().clear();
		
		for (Project validatedProject : validatedProjects) {
		    found.getProjects().add(validatedProject);
		    validatedProject.getExpressions().add(found);
		    projectRepo.save(validatedProject);
		}
		
		expRepo.save(found);
	}

	@Override
	public void deleteGeneExpressionById(long id) throws Exception {
		GeneExpression exp = retrieveGeneExpressionById(id);
		
		for(Project tempP : exp.getProjects()) {
			tempP.getExpressions().remove(exp);
			projectRepo.save(tempP);
		}
		
		exp.getProjects().clear();
		expRepo.save(exp);
		expRepo.delete(exp);
	}

}
