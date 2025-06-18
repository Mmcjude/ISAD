package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "GeneFragmentLength")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeneFragmentLength {
	@Column(name = "GeneFragmentId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long geneFragmentId;
	
	@Column(name = "Name")
	@NotNull(message = "Gene Fragment Length name is required.")
	@Size(min = 2, max = 100, message = "Gene Fragment Length name must be between 2 and 100 characters.")
	private String name;
	
	@Column(name = "PublicationReference")
	@NotNull(message = "Publication reference is required.")
	@Size(min = 5, max = 1000, message = "Publication reference must be between 5 and 1000 characters.")
	private String publicationReference;
	
	@Column(name = "FragmentLength")
	@Positive(message = "Fragment length must be a positive number.")
	private int fragmentLength;
	
	@ManyToOne
	@JoinColumn(name = "SampleId", nullable = true)
	private Samples sample;
	
	@ManyToMany
	@JoinTable(name = "GeneFragment_Project",
		joinColumns = @JoinColumn(name = "GeneFragmentId"),
		inverseJoinColumns = @JoinColumn(name = "ProjectId"))
	@ToString.Exclude
	private Collection<Project> projects = new ArrayList<Project>();
	
	public GeneFragmentLength(String name, String publicationReference,
			int fragmentLength, Samples sample, ArrayList<Project> projects) {
		setName(name);
		setPublicationReference(publicationReference);
		setFragmentLength(fragmentLength);
		setSample(sample);
		setProjects(projects);
	}
}
