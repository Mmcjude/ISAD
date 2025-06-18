package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;

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

import com.example.demo.model.Project;
import com.example.demo.model.Sequencing;
import com.example.demo.service.IProjectCRUDService;
import com.example.demo.service.ISamplesCRUDService;
import com.example.demo.service.ISequencingCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/unit-of-genetics-and-breeding/sequencing")
public class SequencingCRUDController {
	@Autowired
	private ISequencingCRUDService seqService;
	
	@Autowired
	private ISamplesCRUDService sampleService;
	
	@Autowired
	private IProjectCRUDService projectService;
	
	@GetMapping("/create") // localhost:8080/unit-of-genetics-and-breeding/sequencing/create
    public String getControllerCreateSequencingRecord(Model model) {
		Sequencing sequencing = new Sequencing();
		if (sequencing.getDate() == null) {
			sequencing.setDate(LocalDate.now());
		}
        model.addAttribute("sequencing", sequencing);
        model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        model.addAttribute("projects", projectService.retrieveAllProjectRecords());
        return "sequencing/create-sequencing-page";
    }

    @PostMapping("/create")	// localhost:8080/unit-of-genetics-and-breeding/sequencing/create
    public String postControllerCreateSequencingRecord(@Valid @ModelAttribute("sequencing") Sequencing sequencing, 
            BindingResult result, 
            Model model) {
    	
        if (result.hasErrors()) {
        	model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        	model.addAttribute("projects", projectService.retrieveAllProjectRecords());
        	return "sequencing/create-sequencing-page";
        }

        try {            
            seqService.insertNewSequencingRecord(sequencing.getName(), sequencing.getPublicationReference(), sequencing.getDate(), 
            		sequencing.getGeneSequence(), sequencing.getFragmentLength(), sequencing.getSample(), new ArrayList<Project>(sequencing.getProjects()));
            return "redirect:/unit-of-genetics-and-breeding/sequencing/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/unit-of-genetics-and-breeding/sequencing/all
    public String getControllerAllSequencingRecords(Model model, Authentication authentication) {
    	try {
			model.addAttribute("sequencing", seqService.retrieveAllSequencingRecords());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
			return "sequencing/sequencing-list";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
            return "error-page";
		}   
    }
    
    @GetMapping("/details/{id}") // localhost:8080/unit-of-genetics-and-breeding/sequencing/details/{id}
    public String getControllerSequencingRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
        	model.addAttribute("sequencing", seqService.retrieveSequencingRecordById(id));
            return "sequencing/sequencing-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/sequencing/update/{id}
    public String getControllerUpdateSequencingRecord(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("sequencing", seqService.retrieveSequencingRecordById(id));
            model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
            model.addAttribute("projects", projectService.retrieveAllProjectRecords());
            return "sequencing/edit-sequencing-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @PostMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/sequencing/update/{id}
    public String postControllerUpdateSequencingRecord(@PathVariable("id") long id,
    		@Valid @ModelAttribute("sequencing") Sequencing sequencing, 
            BindingResult result, 
            Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        	model.addAttribute("projects", projectService.retrieveAllProjectRecords());
            return "sequencing/edit-sequencing-page";
        }

        try {          
        	seqService.updateSequencingRecordBy(id, sequencing.getName(), sequencing.getPublicationReference(), sequencing.getDate(), 
        			sequencing.getGeneSequence(), sequencing.getFragmentLength(), sequencing.getSample(), new ArrayList<Project>(sequencing.getProjects()));
            return "redirect:/unit-of-genetics-and-breeding/sequencing/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/unit-of-genetics-and-breeding/sequencing/delete/{id}
    public String getControllerDeleteSequencing(@PathVariable("id") long id, Model model) {
        try {
            seqService.deleteSequencingRecordById(id);
            return "redirect:/unit-of-genetics-and-breeding/sequencing/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
}
