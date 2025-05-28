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

import com.example.demo.model.LocalCordinateSystem;
import com.example.demo.service.ILocalCordinateSystemCRUDService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/gis/local-cordinate-system")
public class LocalCordinateSystemCRUDController {
	@Autowired
	private ILocalCordinateSystemCRUDService localService;
	
	@GetMapping("/create") // localhost:8080/gis/local-cordinate-system/create
    public String getControllerCreateLocalLocationRecord(Model model) {
        LocalCordinateSystem location = new LocalCordinateSystem();
        model.addAttribute("location", location);
        return "local-location/create-location-page";
    }

    @PostMapping("/create") // localhost:8080/gis/local-cordinate-system/create
    public String postControllerCreateLocalLocationRecord(@Valid @ModelAttribute("location") LocalCordinateSystem location,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "local-location/create-location-page";
        }

        try {
            localService.insertNewLocalLocationRecord(location.getFieldNumber(), location.getRow(), location.getNumber());
            return "redirect:/gis/local-cordinate-system/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/gis/local-cordinate-system/all
    public String getControllerAllLocalLocationRecords(Model model, Authentication authentication) {
        try {
            model.addAttribute("locations", localService.retrieveAllLocalLocationRecords());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
            return "local-location/location-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/details/{id}") // localhost:8080/gis/local-cordinate-system/details/{id}
    public String getControllerLocalLocationRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
            model.addAttribute("location", localService.retrieveLocalLocationRecordById(id));
            return "local-location/location-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/gis/local-cordinate-system/update/{id}
    public String getControllerUpdateLocalLocationRecordById(@PathVariable("id") long id, Model model) {
        try {
            LocalCordinateSystem location = localService.retrieveLocalLocationRecordById(id);
            model.addAttribute("location", location);
            return "local-location/edit-location-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/gis/local-cordinate-system/update/{id}
    public String postControllerUpdateLocalLocationRecordById(@PathVariable(name = "id") long id,
                                       @Valid @ModelAttribute("location") LocalCordinateSystem location,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "local-location/edit-location-page";
        }

        try {
            localService.updateLocalLocationRecordBy(id, location.getFieldNumber(), location.getRow(), location.getNumber());
            return "redirect:/gis/local-cordinate-system/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/gis/local-cordinate-system/delete/{id}
    public String getControllerDeleteLocalLocationById(@PathVariable(name ="id") long id) {
        try {
            localService.deleteLocalLocationRecordById(id);
            return "redirect:/gis/local-cordinate-system/all";
        } catch (Exception e) {
            return "error-page";
        }
    }    
}
