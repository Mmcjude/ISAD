package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

import com.example.demo.model.Samples;
import com.example.demo.model.WeatherStation;
import com.example.demo.service.ISamplesCRUDService;
import com.example.demo.service.IWeatherStationCRUDService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/weather-station")
public class WeatherStationCRUDController {
	@Autowired
	private IWeatherStationCRUDService weatherStationService;
	
	@Autowired
	private ISamplesCRUDService sampleService;
	
	@GetMapping("/create") // localhost:8080/weather-station/create
    public String getControllerCreateWeatherRecord(Model model) {
        WeatherStation newWeatherStation = new WeatherStation();
        model.addAttribute("weatherStation", newWeatherStation);
        return "weather/create-weather-page";
    }

    @PostMapping("/create") // localhost:8080/weather-station/create
    public String postControllerCreateWeatherRecord(@Valid @ModelAttribute("weatherStation") WeatherStation weatherStation,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "weather/create-weather-page";
        }

        try {
            weatherStationService.insertNewWeatherStationRecord(
                    weatherStation.getTimeType(),
                    weatherStation.getDateTime(),
                    weatherStation.getTemperatureCelcius(),
                    weatherStation.getWindSpeed(),
                    weatherStation.getCurrentRain(),
                    weatherStation.getTotalRain(),
                    weatherStation.getWindDirection(),
                    weatherStation.getSolarRadiation(),
                    weatherStation.getBarometer());
            return "redirect:/weather-station/all";
        } catch (Exception e) {
        	e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/all") // localhost:8080/weather-station/all
    public String getControllerAllWeatherStationRecords(Model model, Authentication authentication) {
        try {
            model.addAttribute("weatherStations", weatherStationService.retrieveAllWeatherStationRecords());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("role", role);
            return "weather/weather-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
    
    @GetMapping("/details/{id}") // localhost:8080/weather-station/details/{id}
    public String getControllerWeatherStationRecordById(@PathVariable(name = "id") long id, Model model) {
        try {
            WeatherStation weatherStation = weatherStationService.retrieveWeatherStationRecordById(id);
            LocalDate weatherDate = weatherStation.getDateTime().toLocalDate();
            List<Samples> allSamples = sampleService.retrieveAllSampleRecords();

            List<Samples> samplesCollectedToday = allSamples.stream()
                .filter(sample -> sample.getLocalDate().isEqual(weatherDate))
                .collect(Collectors.toList());
            model.addAttribute("weatherStation", weatherStation);
            model.addAttribute("samplesToday", samplesCollectedToday);
            return "weather/weather-record";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/update/{id}") // localhost:8080/weather-station/update/{id}
    public String getControllerUpdateWeatherRecordById(@PathVariable("id") long id, Model model) {
        try {
            WeatherStation weatherStation = weatherStationService.retrieveWeatherStationRecordById(id);
            model.addAttribute("weatherStation", weatherStation);
            return "weather/edit-weather-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}") // localhost:8080/weather-station/update/{id}
    public String postControllerUpdateWeatherRecordById(@PathVariable(name = "id") long id,
                                       @Valid @ModelAttribute("weatherStation") WeatherStation weatherStation,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "weather/edit-weather-page";
        }

        try {
            weatherStationService.updateWeatherStationRecordById(
                    id,
                    weatherStation.getTimeType(),
                    weatherStation.getDateTime(),
                    weatherStation.getTemperatureCelcius(),
                    weatherStation.getWindSpeed(),
                    weatherStation.getCurrentRain(),
                    weatherStation.getTotalRain(),
                    weatherStation.getWindDirection(),
                    weatherStation.getSolarRadiation(),
                    weatherStation.getBarometer());
            return "redirect:/weather-station/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}") // localhost:8080/weather-station/delete/{id}
    public String getControllerDeleteWeatherStationById(@PathVariable(name ="id") long id) {
        try {
            weatherStationService.deleteWeatherStationRecordById(id);
            return "redirect:/weather-station/all";
        } catch (Exception e) {
            return "error-page";
        }
    }    
}
