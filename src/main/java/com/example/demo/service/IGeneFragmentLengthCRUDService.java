package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import com.example.demo.model.GeneFragmentLength;
import com.example.demo.model.Project;
import com.example.demo.model.Samples;

public interface IGeneFragmentLengthCRUDService {
	public abstract void insertNewGeneFragmentLength(String name, String publicationReference,
			int fragmentLength, Samples sample, ArrayList<Project> projects) throws Exception;
	public abstract List<GeneFragmentLength> retrieveAllGeneFragmentLengths();
	public abstract GeneFragmentLength retrieveGeneFragmentLengthById(long id) throws Exception;
	public abstract void updateGeneFragmentLengthById(long id, String name, String publicationReference,
			int fragmentLength, Samples sample, ArrayList<Project> projects) throws Exception;
	public abstract void deleteGeneFragmentLengthById(long id) throws Exception;
}
