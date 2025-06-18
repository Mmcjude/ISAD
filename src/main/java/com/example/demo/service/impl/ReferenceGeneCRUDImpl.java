package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Project;
import com.example.demo.model.ReferenceGene;
import com.example.demo.model.Samples;
import com.example.demo.repo.IProjectRepo;
import com.example.demo.repo.IReferenceGeneRepo;
import com.example.demo.service.IReferenceGeneCRUDService;

@Service
public class ReferenceGeneCRUDImpl implements IReferenceGeneCRUDService {
	@Autowired
	private IReferenceGeneRepo refgenRepo;
	
	@Autowired
	private IProjectRepo projectRepo;
	
	@Override
	public void insertNewReferenceGene(String geneReferencecName, String publicationReference, LocalDate date,
			String geneSequence, Samples sample, ArrayList<Project> projects) throws Exception {
		if(geneReferencecName == null || geneReferencecName.trim().isEmpty() ||
				publicationReference == null || publicationReference.trim().isEmpty() ||
				date == null || geneSequence == null || geneSequence.trim().isEmpty() ||
				sample == null) {
			throw new Exception("Reference Gene name, publication, date, gene sequence, and sample must not be null or empty.");
		}
		
		if(geneReferencecName.trim().length() < 2 || geneReferencecName.trim().length() > 100) {
		    throw new Exception("Reference Gene name must be between 2 and 100 characters long.");
		}
		
		if(publicationReference.trim().length() < 5 || publicationReference.trim().length() > 1000) {
			throw new Exception("Publication reference must be between 5 and 1000 characters long.");
		}
		
		if(!geneSequence.matches("^[ACGTNU]+$")) {
			throw new Exception("Gene sequence contains invalid characters. Only capital letters A, C, G, T, N, and U are allowed.");
		}
		
		if(refgenRepo.existsByGeneReferenceName(geneReferencecName.trim())){
			 throw new Exception("Reference Gene with the name '" + geneReferencecName.trim() + "' already exists. Name must be unique.");
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
		
		ReferenceGene refGen = new ReferenceGene(geneReferencecName.trim(), publicationReference.trim(), date, geneSequence.trim(), sample, validatedProjects);
		refgenRepo.save(refGen);
		
		for(Project project : validatedProjects) {
			project.getReferenceGenes().add(refGen);
			projectRepo.save(project);
		}
	}

	@Override
	public List<ReferenceGene> retrieveAllReferenceGenes() {
		return (ArrayList<ReferenceGene>) refgenRepo.findAllByOrderByReferenceGeneIdAsc();
	}

	@Override
	public ReferenceGene retrieveReferenceGeneById(long id) throws Exception {
		if (id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
		}

		if (!refgenRepo.existsById(id)) {
			throw new Exception("Reference Gene table record with ID " + id + " doesn't exist.");
		}
        
		return refgenRepo.findById(id).get();   
	}

	@Override
	public void updateReferenceGeneById(long id, String geneReferencecName, String publicationReference, LocalDate date,
			String geneSequence, Samples sample, ArrayList<Project> projects) throws Exception {
		if(geneReferencecName == null || geneReferencecName.trim().isEmpty() ||
				publicationReference == null || publicationReference.trim().isEmpty() ||
				date == null || geneSequence == null || geneSequence.trim().isEmpty() ||
				sample == null) {
			throw new Exception("Reference Gene name, publication, date, gene sequence, and sample must not be null or empty.");
		}
		
		if(geneReferencecName.trim().length() < 2 || geneReferencecName.trim().length() > 100) {
		    throw new Exception("Reference Gene name must be between 2 and 100 characters long.");
		}
		
		if(publicationReference.trim().length() < 5 || publicationReference.trim().length() > 1000) {
			throw new Exception("Publication reference must be between 5 and 1000 characters long.");
		}
		
		if(!geneSequence.matches("^[ACGTNU]+$")) {
			throw new Exception("Gene sequence contains invalid characters. Only capital letters A, C, G, T, N, and U are allowed.");
		}
		
		ReferenceGene found = retrieveReferenceGeneById(id);
		
		 Optional<ReferenceGene> existing = refgenRepo.findByGeneReferenceName(geneReferencecName.trim());
		 if (existing.isPresent() && existing.get().getReferenceGeneId() != id) {
			 throw new Exception("Reference Gene with the name '" + geneReferencecName.trim() + "' already exists. Name must be unique.");
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
		 
		 found.setGeneReferenceName(geneReferencecName);
		 found.setPublicationReference(publicationReference);
		 found.setDate(date);
		 found.setSample(sample);
		 found.setGeneSequence(geneSequence);
		 
		 for (Project oldProject : found.getProjects()) {
			 oldProject.getReferenceGenes().remove(found);
		 }
		 found.getProjects().clear();
			
		 for (Project validatedProject : validatedProjects) {
			 found.getProjects().add(validatedProject);
			 validatedProject.getReferenceGenes().add(found);
			 projectRepo.save(validatedProject);
		 }
		 
		 refgenRepo.save(found);
	}

	@Override
	public void deleteReferenceGeneById(long id) throws Exception {
		ReferenceGene refGen = retrieveReferenceGeneById(id);
		
		for(Project tempP : refGen.getProjects()) {
			tempP.getReferenceGenes().remove(refGen);
			projectRepo.save(tempP);
		}
		
		refGen.getProjects().clear();
		refgenRepo.save(refGen);
		refgenRepo.delete(refGen);
	}
}
