package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GeneExpression;
import com.example.demo.model.GeneFragmentLength;
import com.example.demo.model.Project;
import com.example.demo.model.ReferenceGene;
import com.example.demo.model.Sequencing;
import com.example.demo.repo.IGeneExpressionRepo;
import com.example.demo.repo.IGeneFragmentLengthRepo;
import com.example.demo.repo.IProjectRepo;
import com.example.demo.repo.IReferenceGeneRepo;
import com.example.demo.repo.ISequencingRepo;
import com.example.demo.service.IProjectCRUDService;

@Service
public class ProjectCRUDServiceImpl implements IProjectCRUDService {
	@Autowired
	private IProjectRepo projectRepo;
	
	@Autowired
	private IGeneFragmentLengthRepo gflRepo;
	
	@Autowired
	private IReferenceGeneRepo refGenRepo;
	
	@Autowired
	private ISequencingRepo seqRepo;
	
	@Autowired
	private IGeneExpressionRepo expRepo;
	
	@Override
	public void insertNewProjectRecord(int projectNr, String title, String description) throws Exception {
		if(title == null || title.isBlank() || description == null || description.isBlank()) {
			throw new Exception("Input parameters 'Title' and 'Description' cannot be null or empty.");
		}
		
		if(!title.trim().matches("[A-ZĀ-Ža-zā-ž0-9/&',.?! ]{3,90}")) {
			throw new Exception("Title must be between 3 and 90 characters and can only contain letters, numbers, spaces, and the following characters: / & ' , . ? !");
		}
		
		if(description.trim().length() > 2000) {
			throw new Exception("Description is too long. It must be 2000 characters or fewer.");
		}
		
		if(projectNr < 0) {
			throw new Exception("Porject with this number already exists in the system.");
		}
		
		if (projectRepo.existsByProjectNr(projectNr)) {
			throw new Exception("A project with this number already exists. Project numbers must be unique.");
		}

		if (projectRepo.existsByTitle(title.trim())) {
			throw new Exception("A project with this title already exists. Project titles must be unique.");
		}

		projectRepo.save(new Project(projectNr, title.trim(), description.trim()));
	}

	@Override
	public List<Project> retrieveAllProjectRecords() {
		return (ArrayList<Project>) projectRepo.findAllByOrderByProjectIdAsc();
	}

	@Override
	public Project retrieveProjectRecordById(long id) throws Exception {
		if(id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
		}
		
		if(!projectRepo.existsById(id)) {
			throw new Exception("Project table record with ID " + id + " does not exist.");
		}
		
		return projectRepo.findById(id).get();
	}

	@Override
	public void updateProjectRecordBy(long id, int projectNr, String title, String description) throws Exception {
		if(title == null || title.isBlank() || description == null || description.isBlank()) {
			throw new Exception("Input parameters 'Title' and 'Description' cannot be null or empty.");
		}
		
		if(!title.trim().matches("[A-ZĀ-Ža-zā-ž0-9/&',.?! ]{3,90}")) {
			throw new Exception("Title must be between 3 and 90 characters and can only contain letters, numbers, spaces, and the following characters: / & ' , . ? !");
		}
		
		if(description.trim().length() > 2000) {
			throw new Exception("Description is too long. It must be 2000 characters or fewer.");
		}
		
		if(projectNr < 0) {
			throw new Exception("Porject with this number already exists in the system.");
		}
		
		Project existing = retrieveProjectRecordById(id);

		if (projectRepo.existsByProjectNrAndProjectIdNot(projectNr, id)) {
			throw new Exception("Another project with this number already exists.");
		}

		if (projectRepo.existsByTitleAndProjectIdNot(title.trim(), id)) {
			throw new Exception("Another project with this title already exists.");
		}
		
		existing.setProjectNr(projectNr);
		existing.setTitle(title.trim());
		existing.setDescription(description.trim());
		projectRepo.save(existing);
	}

	@Override
	public void deleteProjectRecordById(long id) throws Exception {
		Project project = retrieveProjectRecordById(id);
		
		for(GeneFragmentLength gene: project.getGeneFragments()) {
			gene.getProjects().remove(project);
			gflRepo.save(gene);
		}
		project.getGeneFragments().clear();
		
		for(ReferenceGene refGen : project.getReferenceGenes()) {
			refGen.getProjects().remove(project);
			refGenRepo.save(refGen);
		}
		project.getReferenceGenes().clear();
		
		for(Sequencing seq : project.getSequencing()) {
			seq.getProjects().remove(project);
			seqRepo.save(seq);
		}
		project.getSequencing().clear();
		
		for(GeneExpression exp : project.getExpressions()) {
			exp.getProjects().remove(project);
			expRepo.save(exp);
		}
		project.getExpressions().clear();
		
		projectRepo.save(project);
		projectRepo.delete(project);
	}

}
