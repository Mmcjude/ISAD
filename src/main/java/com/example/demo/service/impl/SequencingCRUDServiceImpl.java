package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Project;
import com.example.demo.model.Samples;
import com.example.demo.model.Sequencing;
import com.example.demo.repo.IProjectRepo;
import com.example.demo.repo.ISequencingRepo;
import com.example.demo.service.ISequencingCRUDService;

@Service
public class SequencingCRUDServiceImpl implements ISequencingCRUDService {
	@Autowired
	private ISequencingRepo seqRepo;
	
	@Autowired
	private IProjectRepo projectRepo;

	@Override
	public void insertNewSequencingRecord(String name, String publicationReference, LocalDate date, String geneSequence,
			int fragmentLength, Samples sample, ArrayList<Project> projects) throws Exception {
		if(name == null || name.isBlank() || publicationReference == null || publicationReference.isBlank() ||
				date == null || geneSequence == null || geneSequence.isBlank() || sample == null) {
			throw new Exception("Name, publication reference, date, gene sequence, and sample must not be null or empty.");
		}
		
		if(name.trim().length() < 2 || name.trim().length() > 100) {
		    throw new Exception("Name must be between 2 and 100 characters long.");
		}
		
		if(publicationReference.trim().length() < 5 || publicationReference.trim().length() > 1000) {
			throw new Exception("Publication reference must be between 5 and 1000 characters long.");
		}
		
		if (fragmentLength < 1) {
			throw new Exception("Fragment length must be a positive number greater than zero.");
		}
		
		if(!geneSequence.matches("^[ACGTNU]+$")) {
			throw new Exception("Gene sequence contains invalid characters. Only capital letters A, C, G, T, N, and U are allowed.");
		}
		
		if (seqRepo.existsByName(name.trim())) {
		    throw new Exception("A sequencing record with the name '" + name.trim() + "' already exists.");
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
		
		Sequencing seq = new Sequencing(name.trim(), publicationReference.trim(), date, geneSequence.trim(), fragmentLength,
				sample, validatedProjects);
		seqRepo.save(seq);
		
		for(Project project : validatedProjects) {
			project.getSequencing().add(seq);
			projectRepo.save(project);
		}
	}

	@Override
	public List<Sequencing> retrieveAllSequencingRecords() {
		return (ArrayList<Sequencing>) seqRepo.findAllByOrderBySequencingIdAsc();
	}

	@Override
	public Sequencing retrieveSequencingRecordById(long id) throws Exception {
		if(id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
		}
		
		if(!seqRepo.existsById(id)) {
			 throw new Exception("Sequencing table record with id " + id + " doesn't exist.");
		}
		
		return seqRepo.findById(id).get();
	}

	@Override
	public void updateSequencingRecordBy(long id, String name, String publicationReference, LocalDate date,
			String geneSequence, int fragmentLength, Samples sample, ArrayList<Project> projects) throws Exception {
		if(name == null || name.isBlank() || publicationReference == null || publicationReference.isBlank() ||
				date == null || geneSequence == null || geneSequence.isBlank() || sample == null) {
			throw new Exception("Name, publication reference, date, gene sequence, and sample must not be null or empty.");
		}
		
		if(name.trim().length() < 2 || name.trim().length() > 100) {
		    throw new Exception("Name must be between 2 and 100 characters long.");
		}
		
		if(publicationReference.trim().length() < 5 || publicationReference.trim().length() > 1000) {
			throw new Exception("Publication reference must be between 5 and 1000 characters long.");
		}
		
		if (fragmentLength < 1) {
			throw new Exception("Fragment length must be a positive number greater than zero.");
		}
		
		if(!geneSequence.matches("^[ACGTNU]+$")) {
			throw new Exception("Gene sequence contains invalid characters. Only capital letters A, C, G, T, N, and U are allowed.");
		}
		
		Sequencing found = retrieveSequencingRecordById(id);
		
		if (seqRepo.existsByNameAndSequencingIdNot(name.trim(), id)) {
		    throw new Exception("A sequencing record with the name '" + name.trim() + "' already exists.");
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
		 
		 found.setName(name.trim());
		 found.setPublicationReference(publicationReference.trim());
		 found.setDate(date);
		 found.setFragmentLength(fragmentLength);
		 found.setSample(sample);
		 found.setGeneSequence(geneSequence.trim());
		 
		 for (Project oldProject : found.getProjects()) {
			 oldProject.getSequencing().remove(found);
		 }
		 found.getProjects().clear();
			
		 for (Project validatedProject : validatedProjects) {
			 found.getProjects().add(validatedProject);
			 validatedProject.getSequencing().add(found);
			 projectRepo.save(validatedProject);
		 }
		 
		 seqRepo.save(found);
	}

	@Override
	public void deleteSequencingRecordById(long id) throws Exception {
		Sequencing seq = retrieveSequencingRecordById(id);
		
		for(Project tempP : seq.getProjects()) {
			tempP.getSequencing().remove(seq);
			projectRepo.save(tempP);
		}
		
		seq.getProjects().clear();
		seqRepo.save(seq);
		seqRepo.delete(seq);
	}

}
