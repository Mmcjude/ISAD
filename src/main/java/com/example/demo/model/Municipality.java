package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Municipality")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Municipality {
	@Column(name = "MunicipalityId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long municipalityId;
	
	@Column(name = "MunicipalityName")
	@NotNull
	@Pattern(regexp = "^[A-Za-zĀ-Žā-ž'&\\- ]{2,50}$", 
	message = "Municipality name must be 2 to 50 characters long and can only include letters, apostrophes, ampersands, hyphens, and spaces.")
	private String municipalityName;
	
	@ManyToOne
	@JoinColumn(name = "CountryId", nullable = true)
	private Country country;
	
	@OneToMany(mappedBy = "municipality")
	@ToString.Exclude
	private Collection<GeographicInformationSystem> gisList = new ArrayList<GeographicInformationSystem>();
	
	public Municipality(String inputMunicipalityName, Country inputCountry) {
		setMunicipalityName(inputMunicipalityName);
		setCountry(inputCountry);
	}

}
