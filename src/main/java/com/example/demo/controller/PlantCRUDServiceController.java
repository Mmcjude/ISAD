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

import com.example.demo.model.Plant;
import com.example.demo.service.IGeographicInformationSystemCRUDService;
import com.example.demo.service.IPlantCRUDService;
import com.example.demo.service.IVarietyCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/plant")
public class PlantCRUDServiceController {
	@Autowired
	private IPlantCRUDService plantService;
	
	@Autowired
	private IVarietyCRUDService varService;
	
	@Autowired
	private IGeographicInformationSystemCRUDService gisService;
	
	@GetMapping("/create") // localhost:8080/plant/create
    public String getControllerCreatePlantRecord(Model model) {
        try {
        	Plant plant = new Plant();
            model.addAttribute("plant", plant);
			model.addAttribute("varieties", varService.retrieveAllVarietyRecordsByOrder());
			model.addAttribute("gisExternal", gisService.getGisWithoutLocalCoordinates());
			model.addAttribute("gisRemoved", gisService.getGisWithLocalCoordinates());
			model.addAttribute("gisPlanted", gisService.getGisWithAvailableLocalCoordinates());
			return "plant/create-plant-page";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
            return "error-page";
		}  
    }

    @PostMapping("/create") // localhost:8080/plant/create
    public String postControllerCreatePlantRecord(@Valid @ModelAttribute("plant") Plant plant,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("varieties", varService.retrieveAllVarietyRecordsByOrder());
			model.addAttribute("gisExternal", gisService.getGisWithoutLocalCoordinates());
			model.addAttribute("gisRemoved", gisService.getGisWithLocalCoordinates());
			model.addAttribute("gisPlanted", gisService.getGisWithAvailableLocalCoordinates());
            return "plant/create-plant-page";
        }

        try {
            plantService.insertNewPlantRecord(plant.getVariety(),plant.getOrigin(), plant.getStatus(), 
            		plant.getAdditionalNotes(), plant.getGis());
            return "redirect:/plant/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/plant/all
    public String getControllerAllPlantRecords(Model model, Authentication authentication) {
        try {
            model.addAttribute("plant", plantService.retrieveAllPlantRecords());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
            return "plant/plant-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/details/{id}") // localhost:8080/plant/details/{id}
    public String getControllerPlantRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
            model.addAttribute("plant", plantService.retrievePlantRecordById(id));
            return "plant/plant-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/plant/update/{id}
    public String getControllerUpdatePlantRecordById(@PathVariable("id") long id, Model model) {
        try {
            Plant plant = plantService.retrievePlantRecordById(id);
            model.addAttribute("varieties", varService.retrieveAllVarietyRecordsByOrder());
			model.addAttribute("gisExternal", gisService.getGisWithoutLocalCoordinates());
			model.addAttribute("gisRemoved", gisService.getGisWithLocalCoordinates());
			model.addAttribute("gisPlanted", gisService.getGisWithAvailableLocalCoordinates());
            model.addAttribute("plant", plant);
            return "plant/edit-plant-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/plant/update/{id}
    public String postControllerUpdatePlantRecordById(@PathVariable(name = "id") long id,
                                       @Valid @ModelAttribute("plant") Plant plant,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("varieties", varService.retrieveAllVarietyRecordsByOrder());
			model.addAttribute("gisExternal", gisService.getGisWithoutLocalCoordinates());
			model.addAttribute("gisRemoved", gisService.getGisWithLocalCoordinates());
			model.addAttribute("gisPlanted", gisService.getGisWithAvailableLocalCoordinates());
            return "plant/edit-plant-page";
        }

        try {
            plantService.updatePlantRecordBy(id, plant.getVariety(),plant.getOrigin(), plant.getStatus(), 
            		plant.getAdditionalNotes(), plant.getGis());
            return "redirect:/plant/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/plant/delete/{id}
    public String getControllerDeletePlantById(@PathVariable(name ="id") long id, Model model) {
        try {
            plantService.deletePlantRecordById(id);
            return "redirect:/plant/all";
        } catch (Exception e) {
        	model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    } 
}
