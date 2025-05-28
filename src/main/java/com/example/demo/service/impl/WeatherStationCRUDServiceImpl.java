package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.WeatherStation;
import com.example.demo.model.enums.CardinalDirections;
import com.example.demo.model.enums.TimeType;
import com.example.demo.repo.IWeatherStationRepo;
import com.example.demo.service.IWeatherStationCRUDService;

@Service
public class WeatherStationCRUDServiceImpl implements IWeatherStationCRUDService {
	@Autowired
	private IWeatherStationRepo weatherRepo;
	
	@Override
	public void insertNewWeatherStationRecord(TimeType inputType, LocalDateTime inputTime, float inputTemperature,
			float inputWindSpeed, float inputCurrentRain, float inputTotalRain, 
			CardinalDirections inputWindDirection, float inputRadiation, float inputBarometer) throws Exception {
		if (inputType == null) throw new Exception("TimeType must not be null.");
		if (inputTime == null) throw new Exception("DateTime must not be null.");
		if (inputWindDirection == null) throw new Exception("WindDirection must not be null.");
		if (inputCurrentRain < 0) throw new Exception("Current rain cannot be negative.");
		if (inputTotalRain < 0) throw new Exception("Total rain cannot be negative.");
		
		validateRange(inputTemperature, -50, 50, "Input temperature");
		validateRange(inputWindSpeed, 0, 50, "Input wind speed");
		validateRange(inputRadiation, 0, 1200, "Input solar radiation");
		validateRange(inputBarometer, 980, 1050, "Input barometer");
		
		LocalDateTime startOfDay = inputTime.toLocalDate().atStartOfDay();
	    LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
	    
	    if (weatherRepo.existsByTimeTypeAndDateTimeBetween(inputType, startOfDay, endOfDay)) {
	        throw new Exception("A record for '" + inputType + "' already exists for the specified date.");
	    }
	    
	    int month = inputTime.getMonthValue();
	    int hour = inputTime.getHour();
	    
	    if(inputType == TimeType.Sunrise) {
	    	if (month >= 4 && month <= 9) {
	    		if(hour < 3 || hour > 8) {
	    			throw new Exception("Sunrise in spring-summer months should be between 3:00 AM and 8:00 AM.");
	    		}
	    	} else {
	    		if(hour < 7 || hour > 10) {
	    			throw new Exception("Sunrise in autumn-winter months should be between 7:00 AM and 10:00 AM.");
	    		}
	    	}
	    } else if (inputType == TimeType.Sunset) {
	        if (month >= 4 && month <= 9) {
	            if (hour < 19 || hour > 23) {
	                throw new Exception("Sunset in spring-summer months should be between 7:00 PM and 11:00 PM.");
	            }
	        } else {
	            if (hour < 15 || hour > 18) {
	                throw new Exception("Sunset in autumn-winter months should be between 3:00 PM and 6:00 PM.");
	            }
	        }
	    }
	    
		WeatherStation weatherRecord = new WeatherStation(inputType, inputTime, inputTemperature,
				inputWindSpeed, inputCurrentRain, inputTotalRain, inputWindDirection,
				inputRadiation, inputBarometer);
		
		weatherRepo.save(weatherRecord);
	}

	@Override
	public List<WeatherStation> retrieveAllWeatherStationRecords(){
		return (ArrayList<WeatherStation>) weatherRepo.findAllByOrderByWeatherIdAsc();
	}

	@Override
	public WeatherStation retrieveWeatherStationRecordById(long id) throws Exception {
		if(id <= 0) {
			throw new Exception("Invalid ID: ID must be a positive number.");
		}
		
		if(!weatherRepo.existsById(id)) {
			throw new Exception("Weather Table record with id " + id + " doesn't exist.");
		}
		
		return weatherRepo.findById(id).get();
	}

	@Override
	public void updateWeatherStationRecordById(long id, TimeType inputType, LocalDateTime inputTime, float inputTemperature,
			float inputWindSpeed, float inputCurrentRain, float inputTotalRain, 
			CardinalDirections inputWindDirection, float inputRadiation, float inputBarometer) throws Exception {
		if (inputType == null) throw new Exception("TimeType must not be null.");
		if (inputTime == null) throw new Exception("DateTime must not be null.");
		if (inputWindDirection == null) throw new Exception("WindDirection must not be null.");
		if (inputCurrentRain < 0) throw new Exception("Current rain cannot be negative.");
		if (inputTotalRain < 0) throw new Exception("Total rain cannot be negative.");
		
		validateRange(inputTemperature, -50, 50, "Input temperature");
		validateRange(inputWindSpeed, 0, 50, "Input wind speed");
		validateRange(inputRadiation, 0, 1200, "Input solar radiation");
		validateRange(inputBarometer, 980, 1050, "Input barometer");
		
		WeatherStation foundWeatherRecord = retrieveWeatherStationRecordById(id);
		
		LocalDate inputDate = inputTime.toLocalDate();
		LocalDateTime startOfDay = inputDate.atStartOfDay();
		LocalDateTime endOfDay = inputDate.atTime(LocalTime.MAX);

		Optional<WeatherStation> existing = weatherRepo.findByTimeTypeAndDateTimeBetween(inputType, startOfDay, endOfDay);

		if (existing.isPresent() && existing.get().getWeatherId() != id) {
			throw new Exception("A record for '" + inputType + "' already exists for the specified date.");
		}
		
		int month = inputTime.getMonthValue();
	    int hour = inputTime.getHour();
	    
	    if(inputType == TimeType.Sunrise) {
	    	if (month >= 4 && month <= 9) {
	    		if(hour < 3 || hour > 8) {
	    			throw new Exception("Sunrise in spring-summer months should be between 3:00 AM and 8:00 AM.");
	    		}
	    	} else {
	    		if(hour < 7 || hour > 10) {
	    			throw new Exception("Sunrise in autumn-winter months should be between 7:00 AM and 10:00 AM.");
	    		}
	    	}
	    } else if (inputType == TimeType.Sunset) {
	        if (month >= 4 && month <= 9) {
	            if (hour < 19 || hour > 23) {
	                throw new Exception("Sunset in spring-summer months should be between 7:00 PM and 11:00 PM.");
	            }
	        } else {
	            if (hour < 15 || hour > 18) {
	                throw new Exception("Sunset in autumn-winter months should be between 3:00 PM and 6:00 PM.");
	            }
	        }
	    }
		
		foundWeatherRecord.setTimeType(inputType);
		foundWeatherRecord.setDateTime(inputTime);
		foundWeatherRecord.setWindSpeed(inputWindSpeed);
		foundWeatherRecord.setWindDirection(inputWindDirection);
		foundWeatherRecord.setCurrentRain(inputCurrentRain);
		foundWeatherRecord.setTotalRain(inputTotalRain);
		foundWeatherRecord.setSolarRadiation(inputRadiation);
		foundWeatherRecord.setBarometer(inputBarometer);
		weatherRepo.save(foundWeatherRecord);
	}

	@Override
	public void deleteWeatherStationRecordById(long id) throws Exception {
		weatherRepo.delete(retrieveWeatherStationRecordById(id));
		
	}
	
	private void validateRange(float value, float min, float max, String fieldName) throws Exception {
	    if (value < min || value > max) {
	        throw new Exception(fieldName + " is out of allowed bounds [" + min + ", " + max + "].");
	    }
	}
	
	public List<WeatherStation> findByDate(LocalDate date) {
	    LocalDateTime startOfDay = date.atStartOfDay();
	    LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
	    
	    return weatherRepo.findAllByDateTimeBetween(startOfDay, endOfDay);
	}
}
