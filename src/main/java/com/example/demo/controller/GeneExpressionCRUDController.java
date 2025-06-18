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

import com.example.demo.model.GeneExpression;
import com.example.demo.model.Project;
import com.example.demo.service.IGeneExpressionCRUDService;
import com.example.demo.service.IProjectCRUDService;
import com.example.demo.service.ISamplesCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/unit-of-genetics-and-breeding/gene-expression")
public class GeneExpressionCRUDController {
	@Autowired
	private IGeneExpressionCRUDService expService;
	
	@Autowired
	private ISamplesCRUDService sampleService;
	
	@Autowired
	private IProjectCRUDService projectService;
	
	@GetMapping("/create") // localhost:8080/unit-of-genetics-and-breeding/gene-expression/create
    public String getControllerCreateGeneExpressionRecord(Model model) {
		GeneExpression exp = new GeneExpression();
		if (exp.getDate() == null) {
			exp.setDate(LocalDate.now());
		}
        model.addAttribute("exp", exp);
        model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        model.addAttribute("projects", projectService.retrieveAllProjectRecords());
        return "gene-expression/create-expression-page";
    }

    @PostMapping("/create")	// localhost:8080/unit-of-genetics-and-breeding/gene-expression/create
    public String postControllerCreateGeneExpressionRecord(@Valid @ModelAttribute("exp") GeneExpression exp, 
            BindingResult result, 
            Model model) {
    	
        if (result.hasErrors()) {
        	model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        	model.addAttribute("projects", projectService.retrieveAllProjectRecords());
        	return "gene-expression/create-expression-page";
        }

        try {            
            expService.insertNewGeneExpression(exp.getExpressionName(), exp.getDate(), exp.getHasSample(), exp.getExpressionFoldNumber(), 
            		exp.getGeneSequence(), exp.getSample(), new ArrayList<Project>(exp.getProjects()));
            return "redirect:/unit-of-genetics-and-breeding/gene-expression/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/unit-of-genetics-and-breeding/gene-expression/all
    public String getControllerAllGeneExpressionRecords(Model model, Authentication authentication) {
    	try {
    		model.addAttribute("exp", expService.retrieveAllGeneExpressions());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
			return "gene-expression/expression-list";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
            return "error-page";
		}   
    }
    
    @GetMapping("/details/{id}") // localhost:8080/unit-of-genetics-and-breeding/gene-expression/details/{id}
    public String getControllerGeneExpressionRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
        	model.addAttribute("exp", expService.retrieveGeneExpressionById(id));
            return "gene-expression/expression-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/gene-expression/update/{id}
    public String getControllerUpdateGeneExpressionRecord(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("exp", expService.retrieveGeneExpressionById(id));
            model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
            model.addAttribute("projects", projectService.retrieveAllProjectRecords());
            return "gene-expression/edit-expression-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @PostMapping("/update/{id}") // localhost:8080/unit-of-genetics-and-breeding/gene-expression/update/{id}
    public String postControllerUpdateGeneExpressionRecord(@PathVariable("id") long id,
    		@Valid @ModelAttribute("exp") GeneExpression exp, 
            BindingResult result, 
            Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("samples", sampleService.retrieveAllSampleRecords());
        	model.addAttribute("projects", projectService.retrieveAllProjectRecords());
            return "gene-expression/edit-expression-page";
        }

        try {          
            expService.updateGeneExpressionById(id, exp.getExpressionName(), exp.getDate(), exp.getHasSample(), 
            		exp.getExpressionFoldNumber(), exp.getGeneSequence(), exp.getSample(), new ArrayList<Project>(exp.getProjects()));
            return "redirect:/unit-of-genetics-and-breeding/gene-expression/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/unit-of-genetics-and-breeding/gene-expression/delete/{id}
    public String getControllerDeleteGeneExpression(@PathVariable("id") long id, Model model) {
        try {
            expService.deleteGeneExpressionById(id);
            return "redirect:/unit-of-genetics-and-breeding/gene-expression/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

}
