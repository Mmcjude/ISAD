package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import com.example.demo.model.Plant;
import com.example.demo.model.Samples;
import com.example.demo.model.Species;
import com.example.demo.model.enums.NucleicAcidType;

public interface ISamplesCRUDService {
	public abstract void insertNewSampleRecord(NucleicAcidType inputNucleicType, LocalDate inputCollectionDate, Species inputSpecies,
			float inputConcentration, Plant inputPlant) throws Exception;
	public abstract List<Samples> retrieveAllSampleRecords();
	public abstract Samples retrieveSampleRecordById(long id) throws Exception;
	public abstract void updateSampleRecordBy(long id, NucleicAcidType inputNucleicType, LocalDate inputCollectionDate, Species inputSpecies,
			float inputConcentration, Plant inputPlant) throws Exception;
	public abstract void deleteSampleRecordById(long id) throws Exception;
}
