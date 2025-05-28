package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ReferenceGene")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReferenceGene {
	@Column(name = "ReferenceGeneId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long referenceGeneId;
	
	@Column(name = "GeneReferenceName")
	@NotNull(message = "Gene reference name must not be null.")
	@Size(min = 2, max = 100, message = "Gene reference name must be between 2 and 100 characters long.")
	private String geneReferenceName;
	
	@Column(name = "PublicationReference")
	@NotNull(message = "Publication reference must not be null.")
	@Size(min = 5, max = 1000, message = "Publication reference must be between 5 and 1000 characters long.")
	private String publicationReference;
	
	@Column(name = "Date")
	@NotNull(message = "Date must not be null.")
	private LocalDate date;
	
	@Column(name = "GeneSequence", columnDefinition = "TEXT")
	@NotBlank(message = "Gene sequence must not be blank.")
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Pattern(regexp = "^[ACGTNU]+$", message = "Gene sequence contains invalid characters. Only A, C, G, T, N, and U are allowed.")
	private String geneSequence;

	@ManyToOne
	@JoinColumn(name = "SampleId", nullable = true)
	private Samples sample;
	
	public ReferenceGene(String geneReferencecName, String publicationReference, 
			LocalDate date, String geneSequence, Samples sample) {
		setGeneReferenceName(geneReferencecName);
		setPublicationReference(publicationReference);
		setDate(date);
		setGeneSequence(geneSequence);
		setSample(sample);
	}
}
