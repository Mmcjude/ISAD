package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GeneExpression;
import com.example.demo.model.GeneFragmentLength;
import com.example.demo.model.Plant;
import com.example.demo.model.ReferenceGene;
import com.example.demo.model.Samples;
import com.example.demo.model.Sequencing;
import com.example.demo.model.Species;
import com.example.demo.model.enums.NucleicAcidType;
import com.example.demo.repo.IGeneExpressionRepo;
import com.example.demo.repo.IGeneFragmentLengthRepo;
import com.example.demo.repo.IReferenceGeneRepo;
import com.example.demo.repo.ISamplesRepo;
import com.example.demo.repo.ISequencingRepo;
import com.example.demo.service.ISamplesCRUDService;

import jakarta.transaction.Transactional;

@Service
public class SampleCRUDServiceImpl implements ISamplesCRUDService {
	@Autowired
	private ISamplesRepo sampleRepo;
	
	@Autowired
	private IReferenceGeneRepo refgenRepo;
	
	@Autowired
	private IGeneFragmentLengthRepo fragmentRepo;
	
	@Autowired
	private ISequencingRepo seqRepo;
	
	@Autowired
	private IGeneExpressionRepo expRepo;
	
	@Override
	public void insertNewSampleRecord(NucleicAcidType inputNucleicType, LocalDate inputCollectionDate, Species inputSpecies,
			float inputConcentration, Plant inputPlant) throws Exception {
		if(inputNucleicType == null || inputCollectionDate == null || inputPlant == null || inputSpecies == null) {
			throw new Exception("Nucleic acid type, collection date, species, and plant must not be null.");
		}
		
		if(inputConcentration < 0) {
			 throw new Exception("Input concentration must be a non-negative value.");
		}
		sampleRepo.save(new Samples(inputNucleicType, inputCollectionDate, inputSpecies, inputConcentration, inputPlant));
	}

	@Override
	public List<Samples> retrieveAllSampleRecords(){		
		return (ArrayList<Samples>) sampleRepo.findAllByOrderBySampleIdAsc();
	}

	@Override
	public Samples retrieveSampleRecordById(long id) throws Exception {
		if(id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
		}
		
		if(!sampleRepo.existsById(id)) {
			 throw new Exception("Sample table record with id " + id + " doesn't exist.");
		}
		
		return sampleRepo.findById(id).get();
	}

	@Override
	public void updateSampleRecordBy(long id, NucleicAcidType inputNucleicType, LocalDate inputCollectionDate, Species inputSpecies,
			float inputConcentration, Plant inputPlant) throws Exception {
		if(inputNucleicType == null || inputCollectionDate == null || inputPlant == null || inputSpecies == null) {
			throw new Exception("Nucleic acid type, collection date, species, and plant must not be null.");
		}
		
		if(inputConcentration < 0) {
			 throw new Exception("Input concentration must be a non-negative value.");
		}
		
		Samples foundSamples = retrieveSampleRecordById(id);
		foundSamples.setNucleicType(inputNucleicType);
		foundSamples.setSpecie(inputSpecies);
		foundSamples.setPlant(inputPlant);
		foundSamples.setConcentration(inputConcentration);
		foundSamples.setLocalDate(inputCollectionDate);
		sampleRepo.save(foundSamples);
	}

	@Transactional
	@Override
	public void deleteSampleRecordById(long id) throws Exception {
		Samples sample = retrieveSampleRecordById(id);
		
		for(ReferenceGene refgen : sample.getReferences()) {
			refgen.setSample(null);
			refgenRepo.save(refgen);
		}
		
		for(GeneFragmentLength fragment : sample.getFragments()) {
			fragment.setSample(null);
			fragmentRepo.save(fragment);
		}
		
		for(Sequencing seq : sample.getSequencing()) {
			seq.setSample(null);
			seqRepo.save(seq);
		}
		
		for(GeneExpression exp : sample.getExpressions()) {
			exp.setSample(null);
			expRepo.save(exp);
		}
		
		sampleRepo.delete(sample);
	}
}
