package com.example.demo.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.WeatherStation;
import com.example.demo.model.enums.TimeType;

public interface IWeatherStationRepo extends CrudRepository<WeatherStation, Long> {

	public abstract boolean existsByTimeTypeAndDateTimeBetween(TimeType inputType, LocalDateTime startOfDay, LocalDateTime endOfDay);

	public abstract Optional<WeatherStation> findByTimeTypeAndDateTimeBetween(TimeType inputType,
			LocalDateTime startOfDay, LocalDateTime endOfDay);

	public abstract Optional<WeatherStation> findByTimeTypeAndDateTime(TimeType inputType, LocalDate inputDate);

	public abstract ArrayList<WeatherStation> findAllByOrderByWeatherIdAsc();

	public abstract List<WeatherStation> findAllByDateTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);


}
