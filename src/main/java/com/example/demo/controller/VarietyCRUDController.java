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

import com.example.demo.model.Variety;
import com.example.demo.service.IVarietyCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/plant/variety")
public class VarietyCRUDController {
	@Autowired
	private IVarietyCRUDService varService;
	
	@GetMapping("/create") // localhost:8080/plant/variety/create
    public String getControllerCreateVarietyRecord(Model model) {
        Variety variety = new Variety();
        model.addAttribute("variety", variety);
        return "variety/create-variety-page";
    }

    @PostMapping("/create") // localhost:8080/plant/variety/create
    public String postControllerCreateVarietyRecord(@Valid @ModelAttribute("variety") Variety variety,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "variety/create-variety-page";
        }

        try {
            varService.insertNewVarietyRecord(variety.getName());
            return "redirect:/plant/variety/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/plant/variety/all
    public String getControllerAllVarietyRecords(Model model, Authentication authentication) {
        try {
            model.addAttribute("variety", varService.retrieveAllVarietyRecords());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
            return "variety/variety-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/details/{id}") // localhost:8080/plant/variety/details/{id}
    public String getControllerVarietyRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
            model.addAttribute("variety", varService.retrieveVarietyRecordById(id));
            return "variety/variety-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/update/{id}") // localhost:8080/plant/variety/update/{id}
    public String getControllerUpdateVarietyRecordById(@PathVariable("id") long id, Model model) {
        try {
            Variety variety = varService.retrieveVarietyRecordById(id);
            model.addAttribute("variety", variety);
            return "variety/edit-variety-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/plant/variety/update/{id}
    public String postControllerUpdateVarietyRecordById(@PathVariable(name = "id") long id,
                                       @Valid @ModelAttribute("variety") Variety variety,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "variety/edit-variety-page";
        }

        try {
            varService.updateVarietyRecordById(id, variety.getName());
            return "redirect:/plant/variety/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/plant/variety/delete/{id}
    public String getControllerDeleteGenotypeById(@PathVariable(name ="id") long id) {
        try {
            varService.deleteVarietyRecordById(id);
            return "redirect:/plant/variety/all";
        } catch (Exception e) {
            return "error-page";
        }
    }    
}
