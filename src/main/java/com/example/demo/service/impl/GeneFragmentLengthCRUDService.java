package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GeneFragmentLength;
import com.example.demo.model.Project;
import com.example.demo.model.Samples;
import com.example.demo.repo.IGeneFragmentLengthRepo;
import com.example.demo.repo.IProjectRepo;
import com.example.demo.service.IGeneFragmentLengthCRUDService;

@Service
public class GeneFragmentLengthCRUDService implements IGeneFragmentLengthCRUDService {
	@Autowired
	private IGeneFragmentLengthRepo gflRepo;
	
	@Autowired
	private IProjectRepo projectRepo;
	
	@Override
	public void insertNewGeneFragmentLength(String name, String publicationReference, int fragmentLength,
			Samples sample, ArrayList<Project> projects) throws Exception {
		if(name == null || name.trim().isEmpty() || publicationReference == null || 
				publicationReference.trim().isEmpty() || sample == null) {
			throw new Exception("I parameters must be provided and cannot be empty.");
		}
		
		if(name.trim().length() < 2 || name.trim().length() > 100) {
			throw new Exception("Gene Fragment Length name must be between 2 and 100 characters long.");
		}
		
		if(publicationReference.trim().length() < 5 || publicationReference.trim().length() > 1000) {
			throw new Exception("Publication reference must be between 5 and 1000 characters long.");
		}
		
		if (fragmentLength < 0) {
			throw new Exception("Fragment length must be a positive number greater than zero.");
		}
		
		if(gflRepo.existsByName(name.trim())){
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
		
		GeneFragmentLength gfl = new GeneFragmentLength(name.trim(), publicationReference.trim(), fragmentLength, sample, validatedProjects);
		gflRepo.save(gfl);
		
		for(Project project : validatedProjects) {
			project.getGeneFragments().add(gfl);
			projectRepo.save(project);
		}
	}

	@Override
	public List<GeneFragmentLength> retrieveAllGeneFragmentLengths() {
		return (ArrayList<GeneFragmentLength>) gflRepo.findAllByOrderByGeneFragmentIdAsc();
	}

	@Override
	public GeneFragmentLength retrieveGeneFragmentLengthById(long id) throws Exception {
		if (id <= 0) {
       	 throw new Exception("Invalid ID: ID must be a positive number.");
       }

	     if (!gflRepo.existsById(id)) {
	    	 throw new Exception("No Gene Fragment Length record found with ID " + id + ".");
	     }
	        
	     return gflRepo.findById(id).get();   
	}

	@Override
	public void updateGeneFragmentLengthById(long id, String name, String publicationReference, int fragmentLength,
			Samples sample, ArrayList<Project> projects) throws Exception {
		if(name == null || name.trim().isEmpty() || publicationReference == null || 
				publicationReference.trim().isEmpty() || sample == null) {
			throw new Exception("All input parameters must be provided and cannot be empty.");
		}
		
		if(name.trim().length() < 2 || name.trim().length() > 100) {
			throw new Exception("Name must be between 2 and 100 characters long.");
		}
		
		if(publicationReference.trim().length() < 5 || publicationReference.trim().length() > 1000) {
			throw new Exception("Publication reference must be between 5 and 1000 characters long.");
		}
		
		if (fragmentLength < 0) {
			throw new Exception("Fragment length must be a positive number greater than zero.");
		}
		
		GeneFragmentLength foundGFL = retrieveGeneFragmentLengthById(id);
		
		Optional<GeneFragmentLength> duplicate = gflRepo.findByName(name.trim());
		if (duplicate.isPresent() && duplicate.get().getGeneFragmentId() != id) {
			throw new Exception("Another record with the name '" + name.trim() + "' already exists. Please use a unique name.");
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
		
		foundGFL.setName(name);
		foundGFL.setPublicationReference(publicationReference);
		foundGFL.setFragmentLength(fragmentLength);
		foundGFL.setSample(sample);

		for (Project oldProject : foundGFL.getProjects()) {
		    oldProject.getGeneFragments().remove(foundGFL);
		}
		foundGFL.getProjects().clear();
		
		for (Project validatedProject : validatedProjects) {
		    foundGFL.getProjects().add(validatedProject);
		    validatedProject.getGeneFragments().add(foundGFL);
		    projectRepo.save(validatedProject);
		}
		
		gflRepo.save(foundGFL);
	}

	@Override
	public void deleteGeneFragmentLengthById(long id) throws Exception {
		GeneFragmentLength gfl = retrieveGeneFragmentLengthById(id);
		
		for(Project tempP : gfl.getProjects()) {
			tempP.getGeneFragments().remove(gfl);
			projectRepo.save(tempP);
		}
		
		gfl.getProjects().clear();
		gflRepo.save(gfl);
		gflRepo.delete(gfl);
	}

}
