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

import com.example.demo.model.Habitate;
import com.example.demo.service.IHabitateCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/gis/habitate")
public class HabitateCRUDController {
	@Autowired
	private IHabitateCRUDService habitateService;
	
	@GetMapping("/create") // localhost:8080/gis/habitate/create
    public String getControllerCreateHabitateRecord(Model model) {
		Habitate habitate = new Habitate();
        model.addAttribute("habitate", habitate);
        return "habitate/create-habitate-page";
    }

    @PostMapping("/create") // localhost:8080/gis/habitate/create
    public String postControllerCreateHabitateRecord(@Valid @ModelAttribute("habitate") Habitate genotype,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "habitate/create-habitate-page";
        }

        try {
            habitateService.insertNewHabitateRecord(genotype.getHabitateName());
            return "redirect:/gis/habitate/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/gis/habitate/all
    public String getControllerAllHabitateRecords(Model model, Authentication authentication) {
        try {
            model.addAttribute("habitates", habitateService.retrieveAllHabitateRecords());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
            return "habitate/habitate-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/details/{id}") // localhost:8080/gis/habitate/details/{id}
    public String getControllerHabitateRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
            model.addAttribute("habitate", habitateService.retrieveHabitateById(id));
            return "habitate/habitate-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/gis/habitate/update/{id}
    public String getControllerUpdateHabitateRecordById(@PathVariable("id") long id, Model model) {
        try {
        	Habitate habitate = habitateService.retrieveHabitateById(id);
            model.addAttribute("habitate", habitate);
            return "habitate/edit-habitate-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/gis/habitate/update/{id}
    public String postControllerUpdateHabitateRecordById(@PathVariable(name = "id") long id,
                                       @Valid @ModelAttribute("habitate") Habitate habitate,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "habitate/edit-habitate-page";
        }

        try {
        	habitateService.updateHabitateRecordById(id, habitate.getHabitateName());
            return "redirect:/gis/habitate/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/gis/habitate/delete/{id}
    public String getControllerDeleteHabitateById(@PathVariable(name ="id") long id) {
        try {
        	habitateService.deleteHabitateRecordById(id);
            return "redirect:/gis/habitate/all";
        } catch (Exception e) {
            return "error-page";
        }
    }    
}
