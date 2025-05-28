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
@Table(name = "Variety")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Variety {
	@Column(name = "VarietyId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long varietyId;
	
	@Column(name = "VarietyName")
	@NotNull(message = "Variety name cannot be null.")
	@Pattern(
	    regexp = "^[A-Za-zĀ-Žā-ž'&\\-\\. ]{3,70}$",
	    message = "Variety name must be 3 to 70 characters long and can contain letters, Latvian letters, apostrophes, ampersands, hyphens, dots, or spaces."
	)
	private String name;
	
	@OneToMany(mappedBy = "variety")
	@ToString.Exclude
	private Collection<Plant> plants = new ArrayList<Plant>();
	
	public Variety(String inputName) {
		setName(inputName);
	}
}
