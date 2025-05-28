package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;

import com.example.demo.model.enums.PlantOrigin;
import com.example.demo.model.enums.PlantStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Plant")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Plant {
	@Column(name = "PlantId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long plantId;
	
	@ManyToOne
	@JoinColumn(name = "VarietyId", nullable = true)
	private Variety variety;
	
	@Column(name = "PlantOrigin")
	@NotNull(message = "Plant origin must not be null.")
	@Enumerated(EnumType.STRING)
	private PlantOrigin origin;
	
	@Column(name = "PlantStatus")
	@Enumerated(EnumType.STRING)
	private PlantStatus status;
	
	@Column(name = "AdditionalNotes")
	@Size(max = 1000, message = "Additional notes must not exceed 1000 characters.")
	private String additionalNotes;
	
	@ManyToOne
	@JoinColumn(name = "GisId", nullable = true)
	private GeographicInformationSystem gis;
	
	@OneToMany(mappedBy = "plant")
	@ToString.Exclude
	private Collection<Samples> samples = new ArrayList<Samples>();
	
	public Plant(Variety inputVariety, PlantOrigin inputOrigin,  PlantStatus inputStatues, String inputNotes,
			GeographicInformationSystem inputGis) {
		setVariety(inputVariety);
		setOrigin(inputOrigin);
		setStatus(inputStatues);
		setAdditionalNotes(inputNotes);
		setGis(inputGis);
	}
}
