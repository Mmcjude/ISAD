package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ReferenceGene;
import com.example.demo.model.Samples;
import com.example.demo.repo.IReferenceGeneRepo;
import com.example.demo.service.IReferenceGeneCRUDService;

@Service
public class ReferenceGeneCRUDImpl implements IReferenceGeneCRUDService {
	@Autowired
	private IReferenceGeneRepo refgenRepo;

	@Override
	public void insertNewReferenceGene(String geneReferencecName, String publicationReference, LocalDate date,
			String geneSequence, Samples sample) throws Exception {
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
		
		refgenRepo.save(new ReferenceGene(geneReferencecName.trim(), publicationReference.trim(), date, geneSequence.trim(), sample));
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
			String geneSequence, Samples sample) throws Exception {
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
		 
		 found.setGeneReferenceName(geneReferencecName);
		 found.setPublicationReference(publicationReference);
		 found.setDate(date);
		 found.setSample(sample);
		 found.setGeneSequence(geneSequence);
		 refgenRepo.save(found);
	}

	@Override
	public void deleteReferenceGeneById(long id) throws Exception {
		refgenRepo.delete(retrieveReferenceGeneById(id));
	}

}
