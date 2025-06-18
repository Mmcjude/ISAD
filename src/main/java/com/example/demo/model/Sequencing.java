package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Sequencing")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Sequencing {
	@Column(name = "SequencingId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long sequencingId;
	
	@Column(name = "Name")
	@NotNull(message = "Sequencing name must not be null.")
	@Size(min = 2, max = 100, message = "Sequencing name must be between 2 and 100 characters long.")
	private String name;
	
	@Column(name = "Date")
	@NotNull(message = "Date must not be null.")
	private LocalDate date;
	
	@Column(name = "PublicationReference")
	@NotNull(message = "Publication reference must not be null.")
	@Size(min = 5, max = 1000, message = "Publication reference must be between 5 and 1000 characters long.")
	private String publicationReference;
	
	@Column(name = "GeneSequence", columnDefinition = "TEXT")
	@NotBlank(message = "Gene sequence must not be blank.")
	@Basic(fetch = FetchType.EAGER)
	@Pattern(regexp = "^[ACGTNU]+$", message = "Gene sequence contains invalid characters. Only A, C, G, T, N, and U are allowed.")
	private String geneSequence;
	
	@Column(name = "FragmentLength")
	@Positive(message = "Fragment length must be a positive number.")
	private int fragmentLength;
	
	@ManyToOne
	@JoinColumn(name = "SampleId", nullable = true)
	private Samples sample;
	
	@ManyToMany
	@JoinTable(
	    name = "Sequencing_Project",
	    joinColumns = @JoinColumn(name = "SequencingId"),
	    inverseJoinColumns = @JoinColumn(name = "ProjectId")
	)
	@ToString.Exclude
	private Collection<Project> projects = new ArrayList<Project>();
	
	public Sequencing(String name, String publicationReference, LocalDate date, 
			String geneSequence, int fragmentLength, Samples sample, ArrayList<Project> projects) {
		setName(name);
		setPublicationReference(publicationReference);
		setDate(date);
		setGeneSequence(geneSequence);
		setFragmentLength(fragmentLength);
		setSample(sample);
		setProjects(projects);
	}
}
