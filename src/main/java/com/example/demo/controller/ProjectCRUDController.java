package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.ProjectSummary;
import com.example.demo.dto.ReferenceGeneLinkView;
import com.example.demo.model.Project;
import com.example.demo.repo.IProjectRepo;
import com.example.demo.repo.IReferenceGeneRepo;
import com.example.demo.service.IProjectCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/project")
public class ProjectCRUDController {
	@Autowired
	private IProjectCRUDService projectService;
	
	@Autowired
	private IReferenceGeneRepo refRepo;
	
	@Autowired
	private IProjectRepo projectRepo;
	
	
	@GetMapping("/create") // localhost:8080/project/create
    public String getControllerCreateProjectRecord(Model model) {
        try {
            model.addAttribute("project", new Project());
			return "project/create-project-page";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
            return "error-page";
		}  
    }

    @PostMapping("/create") // localhost:8080/project/create
    public String postControllerCreateProjectRecord(@Valid @ModelAttribute("project") Project project,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
        	return "project/create-project-page";
        }

        try {
            projectService.insertNewProjectRecord(project.getProjectNr(), project.getTitle(), project.getDescription());
            return "redirect:/project/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/project/all
    public String getControllerAllProjectRecords(Model model) {
        try {
        	List<ProjectSummary> projects = projectRepo.findAllProjectSummaryWithCounts();
            model.addAttribute("project", projects);
            return "project/project-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/details/{id}") // localhost:8080/project/details/{id}
    public String getControllerProjectRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
            model.addAttribute("project", projectService.retrieveProjectRecordById(id));
            List<ReferenceGeneLinkView> referenceLinks = refRepo.findLinkViewsByProjectId(id);
            model.addAttribute("referenceLinks", referenceLinks);
            return "project/project-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/project/update/{id}
    public String getControllerUpdateProjectRecordById(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("project", projectService.retrieveProjectRecordById(id));
            return "project/edit-project-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/project/update/{id}
    public String postControllerUpdateProjectRecordById(@PathVariable(name = "id") long id,
                                       @Valid @ModelAttribute("project") Project project,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "plant/edit-plant-page";
        }

        try {
            projectService.updateProjectRecordBy(id, project.getProjectNr(), project.getTitle(), project.getDescription());
            return "redirect:/project/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/project/delete/{id}
    public String getControllerDeleteProjectById(@PathVariable(name ="id") long id, Model model) {
        try {
            projectService.deleteProjectRecordById(id);
            return "redirect:/project/all";
        } catch (Exception e) {
        	model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    } 
}
