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
@Table(name = "Species")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Species {
	@Column(name = "SpeciesId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long speciesId;
	
	@Column(name = "SpeciesName")
	@NotNull(message = "Species name must not be null.")
	@Pattern(
			regexp = "^[A-ZĀ-Ž][a-zA-Zā-žĀ-Ž\\- ]{1,49}$",
		    message = "Species name must start with a capital letter and can contain letters (including Latvian), spaces, or hyphens. Length must be 2–50 characters."
	)
	private String name;
	
	@OneToMany(mappedBy = "specie")
	@ToString.Exclude
	private Collection<Samples> samples = new ArrayList<Samples>();

	public Species(String name) {
		setName(name);
	}
}
