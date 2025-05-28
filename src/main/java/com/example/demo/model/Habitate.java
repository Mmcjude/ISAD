package com.example.demo.model;



import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
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
@Table(name = "Habitate")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Habitate {
	@Column(name = "HabitateId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long habitateId;
	
	@Column(name = "HabitateName")
	@NotNull(message = "Habitate name must not be null.")
	@Pattern(regexp = "^[A-Za-zĀ-Žā-ž'\\- ]{2,50}$", message = "Habitate name must be 2 to 50 characters long and can only contain letters, apostrophes, hyphens, and spaces.")
	private String habitateName;
	
	@OneToMany(mappedBy = "habitate", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@ToString.Exclude
	private Collection<GeographicInformationSystem> gisList = new ArrayList<GeographicInformationSystem>();
	
	public Habitate(String inputHabitateName) {
		setHabitateName(inputHabitateName);
	}
}
