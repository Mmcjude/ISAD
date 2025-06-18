package com.example.demo.controller;

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

import com.example.demo.model.GeneFragmentLength;
import com.example.demo.service.IGeneFragmentLengthCRUDService;
import com.example.demo.service.IProjectCRUDService;
import com.example.demo.service.ISamplesCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/unit-of-genetics-and-breeding/gene-fragment-length")
public class GeneFragmentLengthCRUDController {
	@Autowired
	private IGeneFragmentLengthCRUDService gflService;
	
	@Autowired
	private ISamplesCRUDService sampleService;
	
	@Autowired
	private IProjectCRUDService projectService;
	
	@GetMapping("/create") // localhost:8080/unit-of-genetics-and-breeding/gene-fragment-length/create
    public String getControllerCreateGflRecord(Model model) {
        model.addAttribute("gfl", new GeneFragmentLength());
        model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        model.addAttribute("projects", projectService.retrieveAllProjectRecords());
        return "gene-fragment-length/create-gfl-page";
    }

    @PostMapping("/create")	// localhost:8080/unit-of-genetics-and-breeding/gene-fragment-length/create
    public String postControllerCreateGflRecord(@Valid @ModelAttribute("gfl") GeneFragmentLength gfl, 
            BindingResult result, 
            Model model) {
    	
        if (result.hasErrors()) {
        	model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        	model.addAttribute("projects", projectService.retrieveAllProjectRecords());
        	return "gene-fragment-length/create-gfl-page";
        }

        try {            
            gflService.insertNewGeneFragmentLength(gfl.getName(), gfl.getPublicationReference(), 
            		gfl.getFragmentLength(), gfl.getSample(), new ArrayList<>(gfl.getProjects()));
            return "redirect:/unit-of-genetics-and-breeding/gene-fragment-length/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/unit-of-genetics-and-breeding/gene-fragment-length/all
    public String getControllerAllGflRecords(Model model, Authentication authentication) {
    	try {
			model.addAttribute("gfl", gflService.retrieveAllGeneFragmentLengths());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
			return "gene-fragment-length/gfl-list";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
            return "error-page";
		}   
    }
    
    @GetMapping("/details/{id}") // localhost:8080/unit-of-genetics-and-breeding/gene-fragment-length/details/{id}
    public String getControllerGflRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
        	model.addAttribute("gfl", gflService.retrieveGeneFragmentLengthById(id));
            return "gene-fragment-length/gfl-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/gene-fragment-length/update/{id}
    public String getControllerUpdateGflRecord(@PathVariable("id") long id, Model model) {
        try {
        	GeneFragmentLength foundGfl = gflService.retrieveGeneFragmentLengthById(id);
            model.addAttribute("gfl", foundGfl);
            model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
            model.addAttribute("projects", projectService.retrieveAllProjectRecords());
            return "gene-fragment-length/edit-gfl-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @PostMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/gene-fragment-length/update/{id}
    public String postControllerUpdateGflRecord(@PathVariable("id") long id,
    		@Valid @ModelAttribute("gfl") GeneFragmentLength gfl, 
            BindingResult result, 
            Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        	model.addAttribute("projects", projectService.retrieveAllProjectRecords());
            return "gene-fragment-length/edit-gfl-page";
        }

        try {          
            gflService.updateGeneFragmentLengthById(id, gfl.getName(), gfl.getPublicationReference(),
            		gfl.getFragmentLength(), gfl.getSample(), new ArrayList<>(gfl.getProjects()));
            return "redirect:/unit-of-genetics-and-breeding/gene-fragment-length/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/unit-of-genetics-and-breeding/gene-fragment-length/delete/{id}
    public String getControllerDeleteGfl(@PathVariable("id") long id, Model model) {
        try {
            gflService.deleteGeneFragmentLengthById(id);
            return "redirect:/unit-of-genetics-and-breeding/gene-fragment-length/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
	
}
