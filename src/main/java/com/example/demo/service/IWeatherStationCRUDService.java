package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.model.WeatherStation;
import com.example.demo.model.enums.CardinalDirections;
import com.example.demo.model.enums.TimeType;

public interface IWeatherStationCRUDService {
	public abstract void insertNewWeatherStationRecord(TimeType inputType, LocalDateTime inputTime, float inputTemperature,
			float inputWindSpeed, float inputCurrentRain, float inputTotalRain,
			CardinalDirections inputWindDirection, float inputRadiation, float inputBarometer) throws Exception;
	public abstract List<WeatherStation> retrieveAllWeatherStationRecords();
	public abstract WeatherStation retrieveWeatherStationRecordById(long id) throws Exception;
	public abstract void updateWeatherStationRecordById(long id, TimeType inputType, LocalDateTime inputTime, 
			float inputTemperature, float inputWindSpeed, float inputCurrentRain, 
			float inputTotalRain, CardinalDirections inputWindDirection, float inputRadiation, float inputBarometer) throws Exception;
	public abstract void deleteWeatherStationRecordById(long id) throws Exception;
	
	public List<WeatherStation> findByDate(LocalDate date);
}
