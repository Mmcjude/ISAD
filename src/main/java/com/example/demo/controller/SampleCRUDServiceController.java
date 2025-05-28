package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.ReferenceGeneLinkView;
import com.example.demo.model.Samples;
import com.example.demo.model.WeatherStation;
import com.example.demo.repo.IReferenceGeneRepo;
import com.example.demo.service.IPlantCRUDService;
import com.example.demo.service.ISamplesCRUDService;
import com.example.demo.service.ISpeciesCRUDService;
import com.example.demo.service.IWeatherStationCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/unit-of-genetics-and-breeding/sample")
public class SampleCRUDServiceController {
	@Autowired
	private ISamplesCRUDService sampleService;
	
	@Autowired
	private IPlantCRUDService plantService;
	
	@Autowired
	private ISpeciesCRUDService speciesService;
	
	@Autowired
	private IReferenceGeneRepo refRepo;
	
	@Autowired
	private IWeatherStationCRUDService weatherService;
	
	@GetMapping("/create") // localhost:8080/unit-of-genetics-and-breeding/sample/create
    public String getControllerCreateSampleRecord(Model model) {
        Samples sample = new Samples();
        if (sample.getLocalDate() == null) {
            sample.setLocalDate(LocalDate.now());
        }
        model.addAttribute("sample", sample);
		model.addAttribute("plants", plantService.retrieveAllPlantRecords());
		model.addAttribute("species", speciesService.retrieveAllSpecies());
        return "sample/create-sample-page";
    }

    @PostMapping("/create") // localhost:8080/unit-of-genetics-and-breeding/sample/create
    public String postControllerCreateSampleRecord(@Valid @ModelAttribute("sample") Samples sample,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("plants", plantService.retrieveAllPlantRecords());
        	model.addAttribute("species", speciesService.retrieveAllSpecies());
            return "sample/create-sample-page";
        }

        try {
        	sampleService.insertNewSampleRecord(sample.getNucleicType(), sample.getLocalDate(), sample.getSpecie(), sample.getConcentration(), sample.getPlant());
            return "redirect:/unit-of-genetics-and-breeding/sample/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/unit-of-genetics-and-breeding/sample/all
    public String getControllerAllSampleRecords(Model model, Authentication authentication) {
        try {
            model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
            return "sample/sample-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/details/{id}") // localhost:8080/unit-of-genetics-and-breeding/sample/details/{id}
    public String getControllerSampleRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
        	Samples sample = sampleService.retrieveSampleRecordById(id);
            model.addAttribute("sample", sample);
            List<ReferenceGeneLinkView> references = refRepo.findLinkViewsBySampleId(id);
            model.addAttribute("referenceLinks", references);
            LocalDate collectionDate = sample.getLocalDate();
            List<WeatherStation> weatherRecords = weatherService.findByDate(collectionDate);
            model.addAttribute("weatherRecords", weatherRecords);
            return "sample/sample-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/sample/update/{id}
    public String getControllerUpdateSampleRecordById(@PathVariable("id") long id, Model model) {
        try {
            Samples sample = sampleService.retrieveSampleRecordById(id);
            model.addAttribute("sample", sample);
    		model.addAttribute("plants", plantService.retrieveAllPlantRecords());
    		model.addAttribute("species", speciesService.retrieveAllSpecies());
            return "sample/edit-sample-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/sample/update/{id}
    public String postControllerUpdateSampleRecordById(@PathVariable(name = "id") long id,
                                       @Valid @ModelAttribute("sample") Samples sample,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("plants", plantService.retrieveAllPlantRecords());
        	model.addAttribute("species", speciesService.retrieveAllSpecies());
            return "sample/edit-sample-page";
        }

        try {
            sampleService.updateSampleRecordBy(id,sample.getNucleicType(), sample.getLocalDate(), sample.getSpecie(), sample.getConcentration(), sample.getPlant());
            return "redirect:/unit-of-genetics-and-breeding/sample/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/unit-of-genetics-and-breeding/sample/delete/{id}
    public String getControllerDeleteSampleById(@PathVariable(name ="id") long id) {
        try {
            sampleService.deleteSampleRecordById(id);
            return "redirect:/unit-of-genetics-and-breeding/sample/all";
        } catch (Exception e) {
            return "error-page";
        }
    }    
}
