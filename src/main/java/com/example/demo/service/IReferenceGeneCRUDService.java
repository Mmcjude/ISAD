package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Project;
import com.example.demo.model.ReferenceGene;
import com.example.demo.model.Samples;

public interface IReferenceGeneCRUDService {
	public abstract void insertNewReferenceGene(String geneReferencecName, String publicationReference, 
			LocalDate date, String geneSequence, Samples sample, ArrayList<Project> projects) throws Exception;
	public abstract List<ReferenceGene> retrieveAllReferenceGenes();
	public abstract ReferenceGene retrieveReferenceGeneById(long id) throws Exception;
	public abstract void updateReferenceGeneById(long id, String geneReferencecName, String publicationReference, 
			LocalDate date, String geneSequence, Samples sample, ArrayList<Project> projects) throws Exception;
	public abstract void deleteReferenceGeneById(long id) throws Exception;
}
