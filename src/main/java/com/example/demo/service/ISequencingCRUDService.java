package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Project;
import com.example.demo.model.Samples;
import com.example.demo.model.Sequencing;

public interface ISequencingCRUDService {
	public abstract void insertNewSequencingRecord(String name, String publicationReference, LocalDate date, 
			String geneSequence, int fragmentLength, Samples sample, ArrayList<Project> projects) throws Exception;
	public abstract List<Sequencing> retrieveAllSequencingRecords();
	public abstract Sequencing retrieveSequencingRecordById(long id) throws Exception;
	public abstract void updateSequencingRecordBy(long id, String name, String publicationReference, LocalDate date, 
			String geneSequence, int fragmentLength, Samples sample, ArrayList<Project> projects) throws Exception;
	public abstract void deleteSequencingRecordById(long id) throws Exception;
}
