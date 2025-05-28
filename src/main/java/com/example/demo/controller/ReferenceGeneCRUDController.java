package com.example.demo.controller;

import java.time.LocalDate;

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

import com.example.demo.model.ReferenceGene;
import com.example.demo.repo.IReferenceGeneRepo;
import com.example.demo.service.IReferenceGeneCRUDService;
import com.example.demo.service.ISamplesCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/unit-of-genetics-and-breeding/reference-gene")
public class ReferenceGeneCRUDController {
	@Autowired
	private IReferenceGeneCRUDService refgenService;
	
	@Autowired
	private ISamplesCRUDService sampleService;
	
	@Autowired
	private IReferenceGeneRepo refgenRepo;
	
	@GetMapping("/create") // localhost:8080/unit-of-genetics-and-breeding/reference-gene/create
    public String getControllerCreateReferenceGeneRecord(Model model) {
		ReferenceGene refgen = new ReferenceGene();
		if (refgen.getDate() == null) {
			refgen.setDate(LocalDate.now());
		}
        model.addAttribute("refgen", refgen);
        model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        return "reference-gene/create-refgen-page";
    }

    @PostMapping("/create")	// localhost:8080/unit-of-genetics-and-breeding/reference-gene/create
    public String postControllerCreateReferenceGeneRecord(@Valid @ModelAttribute("refgen") ReferenceGene refgen, 
            BindingResult result, 
            Model model) {
    	
        if (result.hasErrors()) {
        	model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        	return "reference-gene/create-refgen-page";
        }

        try {            
            refgenService.insertNewReferenceGene(refgen.getGeneReferenceName(), refgen.getPublicationReference(), 
            		refgen.getDate(), refgen.getGeneSequence(), refgen.getSample());
            return "redirect:/unit-of-genetics-and-breeding/reference-gene/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/unit-of-genetics-and-breeding/reference-gene/all
    public String getControllerAllReferenceGeneRecords(Model model, Authentication authentication) {
    	try {
			model.addAttribute("refgen", refgenRepo.findSummaryList());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
			return "reference-gene/refgen-list";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
            return "error-page";
		}   
    }
    
    @GetMapping("/details/{id}") // localhost:8080/unit-of-genetics-and-breeding/reference-gene/details/{id}
    public String getControllerReferenceGeneRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
        	model.addAttribute("refgen", refgenService.retrieveReferenceGeneById(id));
            return "reference-gene/refgen-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/reference-gene/update/{id}
    public String getControllerUpdateReferenceGeneRecord(@PathVariable("id") long id, Model model) {
        try {
        	ReferenceGene foundReference = refgenService.retrieveReferenceGeneById(id);
            model.addAttribute("refgen", foundReference);
            model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
            return "reference-gene/edit-refgen-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @PostMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/reference-gene/update/{id}
    public String postControllerUpdateReferenceGeneRecord(@PathVariable("id") long id,
    		@Valid @ModelAttribute("refgen") ReferenceGene refgen, 
            BindingResult result, 
            Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
            return "reference-gene/edit-refgen-page";
        }

        try {          
            refgenService.updateReferenceGeneById(id, refgen.getGeneReferenceName(), refgen.getPublicationReference(), 
            		refgen.getDate(), refgen.getGeneSequence(), refgen.getSample());
            return "redirect:/unit-of-genetics-and-breeding/reference-gene/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/unit-of-genetics-and-breeding/reference-gene/delete/{id}
    public String getControllerDeleteReferenceGene(@PathVariable("id") long id, Model model) {
        try {
            refgenService.deleteReferenceGeneById(id);
            return "redirect:/unit-of-genetics-and-breeding/reference-gene/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

}
