package com.example.demo.controller;

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

import com.example.demo.model.Species;
import com.example.demo.service.ISpeciesCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/unit-of-genetics-and-breeding/species")
public class SpeciesCRUDServiceController {
	@Autowired
	private ISpeciesCRUDService speciesService;
	
	@GetMapping("/create") // localhost:8080/unit-of-genetics-and-breeding/species/create
    public String getControllerCreateSpeciesRecord(Model model) {
        model.addAttribute("species", new Species());
        return "species/create-species-page";
    }

    @PostMapping("/create")	// localhost:8080/unit-of-genetics-and-breeding/species/create
    public String postControllerCreateSpeciesRecord(@Valid @ModelAttribute("species") Species species, 
            BindingResult result, 
            Model model) {
    	
        if (result.hasErrors()) {
        	return "species/create-species-page";
        }

        try {            
        	speciesService.insertNewSpecie(species.getName());
            return "redirect:/unit-of-genetics-and-breeding/species/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/unit-of-genetics-and-breeding/species/all
    public String getControllerAllSpeciesRecords(Model model, Authentication authentication) {
    	try {
			model.addAttribute("species", speciesService.retrieveAllSpecies());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
			return "species/species-list";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
            return "error-page";
		}   
    }
    
    @GetMapping("/details/{id}") // localhost:8080/unit-of-genetics-and-breeding/species/details/{id}
    public String getControllerSpeciesRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
        	model.addAttribute("species", speciesService.retrieveSpeciesById(id));
            return "species/species-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/species/update/{id}
    public String getControllerUpdateSpeciesRecord(@PathVariable("id") long id, Model model) {
        try {
        	Species foundSpecies = speciesService.retrieveSpeciesById(id);
            model.addAttribute("species", foundSpecies);
            return "species/edit-species-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @PostMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/species/update/{id}
    public String postControllerUpdateSpeciesRecord(@PathVariable("id") long id,
    		@Valid @ModelAttribute("species") Species species, 
            BindingResult result, 
            Model model) {
        if (result.hasErrors()) {
            return "species/edit-species-page";
        }

        try {          
            speciesService.updateSpeciesById(id, species.getName());
            return "redirect:/unit-of-genetics-and-breeding/species/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/unit-of-genetics-and-breeding/species/delete/{id}
    public String getControllerDeleteSpecies(@PathVariable("id") long id, Model model) {
        try {
            speciesService.deleteSpeciesById(id);
            return "redirect:/unit-of-genetics-and-breeding/species/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

}
