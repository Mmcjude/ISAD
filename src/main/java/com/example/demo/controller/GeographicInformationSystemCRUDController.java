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

import com.example.demo.model.GeographicInformationSystem;
import com.example.demo.service.IGeographicInformationSystemCRUDService;
import com.example.demo.service.IHabitateCRUDService;
import com.example.demo.service.ILocalCordinateSystemCRUDService;
import com.example.demo.service.IMunicipalityCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/gis/geographic-information-system")
public class GeographicInformationSystemCRUDController {
	@Autowired
	private IGeographicInformationSystemCRUDService gisService;
	
	@Autowired
	private IMunicipalityCRUDService municipalityService;
	
	@Autowired
	private IHabitateCRUDService habitateService;
	
	@Autowired
	private ILocalCordinateSystemCRUDService locationService;
	
	@GetMapping("/create") // localhost:8080/gis/geographic-information-system/create
    public String getControllerCreateGisRecord(Model model) {
        model.addAttribute("gis", new GeographicInformationSystem());
        model.addAttribute("municipalities", municipalityService.retrieveAllMunicipalitiesOrderedByCountryByName());
        model.addAttribute("habitates", habitateService.retrieveAllHabitateOrderedByName());
        model.addAttribute("locations", locationService.getAvailableLocalCoordinatesOrdered());
        return "gis/create-gis-page";
    }

    @PostMapping("/create")	// localhost:8080/gis/geographic-information-system/create
    public String postControllerCreateGisRecord(@Valid @ModelAttribute("gis") GeographicInformationSystem gis, 
            BindingResult result, 
            Model model) {
    	
        if (result.hasErrors()) {
        	model.addAttribute("municipalities", municipalityService.retrieveAllMunicipalitiesOrderedByCountryByName());
            model.addAttribute("habitates", habitateService.retrieveAllHabitateOrderedByName());
            model.addAttribute("locations", locationService.getAvailableLocalCoordinatesOrdered());
            return "gis/create-gis-page";
        }

        try {            
            gisService.insertNewGisRecord(gis.getLatitude(), gis.getLongitude(), gis.getLocalLocation(), gis.getMunicipality(), gis.getHabitate());
            return "redirect:/gis/geographic-information-system/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/gis/geographic-information-system/all
    public String getControllerAllGisRecords(Model model, Authentication authentication) {
    	try {
			model.addAttribute("gisrecs", gisService.retrieveAllGisRecords());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
			return "gis/gis-list";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
            return "error-page";
		}   
    }
    
    @GetMapping("/details/{id}") // localhost:8080/gis/geographic-information-system/details/{id}
    public String getControllerGisRecordById(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("gis", gisService.retrieveGisRecordById(id));
            return "gis/gis-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/gis/geographic-information-system/update/{id}
    public String getControllerUpdateGisRecord(@PathVariable("id") long id, Model model) {
        try {
        	GeographicInformationSystem foundGis = gisService.retrieveGisRecordById(id);
            model.addAttribute("gis", foundGis);
            model.addAttribute("municipalities", municipalityService.retrieveAllMunicipalitiesOrderedByCountryByName());
            model.addAttribute("habitates", habitateService.retrieveAllHabitateOrderedByName());
            
            Long localLocationId = (foundGis.getLocalLocation() != null) ? foundGis.getLocalLocation().getLcsId() : null;
            model.addAttribute("locations", locationService.getAvailableLocalCoordinatesOrderedIncludingCurrent(localLocationId));
            
            return "gis/edit-gis-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/gis/geographic-information-system/update/{id}
    public String postControllerUpdateGisRecord(@PathVariable("id") long id,
    		@Valid @ModelAttribute("gis") GeographicInformationSystem gis, 
            BindingResult result, 
            Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("municipalities", municipalityService.retrieveAllMunicipalitiesOrderedByCountryByName());
            model.addAttribute("habitates", habitateService.retrieveAllHabitateOrderedByName());
            try {
				model.addAttribute("locations", locationService.getAvailableLocalCoordinatesOrderedIncludingCurrent(
				        gis.getLocalLocation() != null ? gis.getLocalLocation().getLcsId() : null));
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
	            return "error-page";
			}
            return "gis/edit-gis-page";
        }

        try {          
            gisService.updateGisRecordById(id, gis.getLatitude(), gis.getLongitude(), gis.getLocalLocation(), gis.getMunicipality(), gis.getHabitate());
            return "redirect:/gis/geographic-information-system/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/gis/geographic-information-system/delete/{id}
    public String getControllerDeleteGis(@PathVariable("id") long id, Model model) {
        try {
            gisService.deleteGisRecordById(id);
            return "redirect:/gis/geographic-information-system/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
}
