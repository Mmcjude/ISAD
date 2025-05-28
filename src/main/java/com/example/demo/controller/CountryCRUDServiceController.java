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

import com.example.demo.model.Country;
import com.example.demo.service.ICountryCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/gis/country")
public class CountryCRUDServiceController {

    @Autowired
    private ICountryCRUDService countryService;

    @GetMapping("/create") // localhost:8080/gis/country/create
    public String getControllerCreateCountryRecord(Model model) {
        model.addAttribute("country", new Country());
        return "country/create-country-page";
    }

    @PostMapping("/create")	// localhost:8080/gis/country/create
    public String postControllerCreateCountryRecord(@Valid @ModelAttribute("country") Country country,
                                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "country/create-country-page";
        }

        try {
            countryService.insertNewCountry(country.getCountryName());
            return "redirect:/gis/country/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/all") // localhost:8080/gis/country/all
    public String getControllerAllCountryRecords(Model model, Authentication authentication) {
        try {
            model.addAttribute("countries", countryService.retrieveAllCountries());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
            return "country/country-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/details/{id}") // localhost:8080/gis/country/details/{id}
    public String getControllerCountryRecordById(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("country", countryService.retrieveCountryById(id));
            return "country/country-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/update/{id}") // localhost:8080/gis/country/update/{id}
    public String getControllerUpdateCountryRecord(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("country", countryService.retrieveCountryById(id));
            return "country/edit-country-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/gis/country/update/{id}
    public String postControllerUpdateCountryRecord(@PathVariable("id") long id,
                                                    @Valid @ModelAttribute("country") Country country,
                                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "country/edit-country-page";
        }

        try {
            countryService.updateCountryById(id, country.getCountryName());
            return "redirect:/gis/country/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/gis/country/delete/{id}
    public String getControllerDeleteCountry(@PathVariable("id") long id, Model model) {
        try {
            countryService.deleteCountryById(id);
            return "redirect:/gis/country/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
}

