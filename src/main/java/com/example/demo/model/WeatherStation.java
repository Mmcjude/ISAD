package com.example.demo.model;

import java.time.LocalDateTime;

import com.example.demo.model.enums.CardinalDirections;
import com.example.demo.model.enums.TimeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "WeatherStation")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WeatherStation {
	
	@Column(name = "WeatherId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long weatherId;
	
	@Column(name = "SunriseOrSunset")
	@NotNull(message = "Time Type must not be null.")
	@Enumerated(EnumType.STRING)
	private TimeType timeType;
	
	@Column(name  = "Datetime")
	@NotNull(message = "DateTime must not be null.")
	private LocalDateTime dateTime;
	
	@Column(name = "TemperatureCelcius")
	@Min(value = -50, message = "Temperature must be at least -50°C.")
	@Max(value = 50, message = "Temperature cannot exceed 50°C.")
	private float temperatureCelcius;
	
	@Column(name = "WindSpeed")
	@Min(value = 0, message = "Wind speed cannot be negative.")
	@Max(value = 50, message = "Wind speed cannot exceed 50 m/s.")
	private float windSpeed; // Meters per Second
	
	@Column(name = "WindDirection")
	@NotNull(message = "Wind direction must not be null.")
	@Enumerated(EnumType.STRING)
	private CardinalDirections windDirection;
	
	@Column(name = "CurrentRain")
	@Min(value = 0, message = "Current rain cannot be negative.")
	private float currentRain; // Millimeters
	
	@Column(name = "TotalRain")
	@Min(value = 0, message = "Total rain cannot be negative.")
	private float totalRain; // Millimeters
	
	@Column(name = "SolarRadiation")
	@Min(value = 0, message = "Solar radiation cannot be negative.")
	@Max(value = 1200, message = "Solar radiation cannot exceed 1200 W/m².")
	private float solarRadiation; // W/m^2
	
	@Column(name = "Barometer")
	@Min(value = 980, message = "Barometric pressure must be at least 980 millibar.")
	@Max(value = 1050, message = "Barometric pressure cannot exceed 1050 millibar.")
	private float barometer; //Millibar
	
	public WeatherStation(TimeType inputType, LocalDateTime inputTime, float inputTemperature,
			float inputWindSpeed, float inputCurrentRain,float inputTotalRain,
			CardinalDirections inputWindDirection, float inputRadiation, float inputBarometer) {
		setTimeType(inputType);
		setDateTime(inputTime);
		setTemperatureCelcius(inputTemperature);
		setWindSpeed(inputWindSpeed);
		setWindDirection(inputWindDirection);
		setCurrentRain(inputCurrentRain);
		setTotalRain(inputTotalRain);
		setSolarRadiation(inputRadiation);
		setBarometer(inputBarometer);
	};
}
