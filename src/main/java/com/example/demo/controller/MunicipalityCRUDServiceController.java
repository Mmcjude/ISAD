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

import com.example.demo.model.Municipality;
import com.example.demo.service.ICountryCRUDService;
import com.example.demo.service.IMunicipalityCRUDService;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/gis/municipality")
public class MunicipalityCRUDServiceController {

    @Autowired
    private IMunicipalityCRUDService municipalityService;

    @Autowired
    private ICountryCRUDService countryService;

    @GetMapping("/create") // localhost:8080/gis/municipality/create
    public String getControllerCreateMunicipalityRecord(Model model) { 
        try {
            model.addAttribute("municipality", new Municipality());
            model.addAttribute("countries", countryService.retrieveCountriesOrderedByName());
            return "municipality/create-municipality-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/create") // localhost:8080/gis/municipality/create
    public String postControllerCreateMunicipalityRecord(@Valid @ModelAttribute("municipality") Municipality municipality,
                                                         BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("countries", countryService.retrieveCountriesOrderedByName());
            return "municipality/create-municipality-page";
        }

        try {
            municipalityService.insertNewMunicipality(municipality.getMunicipalityName(), municipality.getCountry());
            return "redirect:/gis/municipality/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/all") // localhost:8080/gis/municipality/all
    public String getControllerAllMunicipalityRecords(Model model, Authentication authentication) {
        try {
            model.addAttribute("municipalities", municipalityService.retrieveAllMunicipalities());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
            return "municipality/municipality-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/details/{id}") // localhost:8080/gis/municipality/details/{id}
    public String getControllerMunicipalityRecordById(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("municipality", municipalityService.retrieveMunicipalityById(id));
            return "municipality/municipality-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/update/{id}") // localhost:8080/gis/municipality/update/{id}
    public String getControllerUpdateMunicipalityRecord(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("municipality", municipalityService.retrieveMunicipalityById(id));
            model.addAttribute("countries", countryService.retrieveCountriesOrderedByName());
            return "municipality/edit-municipality-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/gis/municipality/update/{id}
    public String postControllerUpdateMunicipalityRecord(@PathVariable("id") long id,
                                                         @Valid @ModelAttribute("municipality") Municipality municipality,
                                                         BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("countries", countryService.retrieveCountriesOrderedByName());
            return "municipality/edit-municipality-page";
        }

        try {
            municipalityService.updateMunicipalityById(id, municipality.getMunicipalityName(), municipality.getCountry());
            return "redirect:/gis/municipality/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/gis/municipality/delete/{id}
    public String getControllerDeleteMunicipality(@PathVariable("id") long id, Model model) {
        try {
            municipalityService.deleteMunicipalityById(id);
            return "redirect:/gis/municipality/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
}

