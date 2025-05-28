package com.example.demo.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "GeographicInformationSystem")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeographicInformationSystem {
	@Column(name = "GisId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long gisId;
	
	@Column(name = "Latitude")
	@DecimalMin(value = "-90.0", inclusive = true, message = "Latitude must be >= -90")
	@DecimalMax(value = "90.0", inclusive = true, message = "Latitude must be <= 90")
    private double latitude;

	@Column(name = "Longitude")
	@DecimalMin(value = "-180.0", inclusive = true, message = "Longitude must be >= -180")
	@DecimalMax(value = "180.0", inclusive = true, message = "Longitude must be <= 180")
    private double longitude;
	
	@OneToOne
	@JoinColumn(name = "LcsId", nullable = true)
	private LocalCordinateSystem localLocation;
	
	@ManyToOne
	@JoinColumn(name = "MunicipalityId", nullable = true)
	private Municipality municipality;
	
	@ManyToOne
	@JoinColumn(name = "HabitateId", nullable = true)
	private Habitate habitate;
	
	@OneToMany(mappedBy = "gis")
	@ToString.Exclude
	private Collection<Plant> plant;
	
	public GeographicInformationSystem(double inputLatitude, double inputLongitude, LocalCordinateSystem localLocation,
			Municipality municipality, Habitate habitate) {
		setLatitude(inputLatitude);
		setLongitude(inputLongitude);
		setLocalLocation(localLocation);
		setHabitate(habitate);
		setMunicipality(municipality);
	}

}
