package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Country")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Country {
	@Column(name = "CountryId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long countryId;
	
	@Column(name = "Country")
	@NotNull(message = "Country name is required.")
	@Pattern(
			regexp = "[A-Za-zĀ-Žā-ž -]{2,50}", 
		    message = "Country name must be 2–50 letters long and can include spaces or hyphens only."
	)
	private String countryName;
	
	@OneToMany(mappedBy = "country")
	@ToString.Exclude
	private Collection<Municipality> municipalities = new ArrayList<Municipality>();
	
	public Country(String countryName) {
		setCountryName(countryName);
	}
}
