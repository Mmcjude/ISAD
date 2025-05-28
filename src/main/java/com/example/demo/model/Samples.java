package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.example.demo.model.enums.NucleicAcidType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Sample")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Samples {
	@Column(name = "SampleId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long sampleId;
	
	@Column(name = "NucleicAcidType")
	@NotNull(message = "Nucleic Acid Type must not be null.")
	private NucleicAcidType nucleicType;
	
	@Column(name = "CollectionDate")
	@NotNull(message = "Collection Date must not be null.")
	private LocalDate localDate;
	
	@Column(name = "Concentration")
	@Min(value = 0, message = "Concentration must be a non-negative value.")
	private float concentration;
	
	@ManyToOne
	@JoinColumn(name = "PlantId", nullable = true)
	private Plant plant;
	
	@ManyToOne
	@JoinColumn(name = "SpeciesId", nullable = true)
	private Species specie;
	
	@OneToMany(mappedBy = "sample")
	@ToString.Exclude
	private Collection<GeneFragmentLength> fragments = new ArrayList<GeneFragmentLength>();
	
	@OneToMany(mappedBy = "sample")
	@ToString.Exclude
	private Collection<ReferenceGene> references = new ArrayList<ReferenceGene>();
	
	public Samples(NucleicAcidType inputNucleicType, LocalDate inputCollectionDate, Species inputSpecies,
			float inputConcentration, Plant inputPlant) {
		setNucleicType(inputNucleicType);
		setLocalDate(inputCollectionDate);
		setSpecie(inputSpecies);
		setConcentration(inputConcentration);
		setPlant(inputPlant);
	}
}
