package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.example.demo.model.enums.HasSample;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "GeneExpression")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeneExpression {
	@Column(name = "ExpressionId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long expressionId;
	
	@Column(name = "ExpressionName")
	@NotNull(message = "Gene Expression name must not be null.")
	@Size(min = 2, max = 100, message = "Gene Expression name must be between 2 and 100 characters long.")
	private String expressionName;
	
	@Column(name = "Date")
	@NotNull(message = "Date must not be null.")
	private LocalDate date;
	
	@Column(name = "HasSample")
	@NotNull(message = "Value must not be null.")
	@Enumerated(EnumType.STRING)
	private HasSample hasSample;

	@Column(name = "ExpressionFoldNumber")
	@Positive(message = "Fold Number must be a positive number.")
	private double expressionFoldNumber;
	
	@Column(name = "GeneSequence", columnDefinition = "TEXT")
	@NotBlank(message = "Gene sequence must not be blank.")
	@Basic(fetch = FetchType.EAGER)
	@Pattern(regexp = "^[ACGTNU]+$", message = "Gene sequence contains invalid characters. Only A, C, G, T, N, and U are allowed.")
	private String geneSequence;
	
	@ManyToOne
	@JoinColumn(name = "SampleId", nullable = true)
	private Samples sample;
	
	@ManyToMany
	@JoinTable(
	    name = "GeneExpression_Project",
	    joinColumns = @JoinColumn(name = "ExpressionId"),
	    inverseJoinColumns = @JoinColumn(name = "ProjectId")
	)
	@ToString.Exclude
	private Collection<Project> projects = new ArrayList<Project>();
	
	public GeneExpression(String name, LocalDate date, HasSample hasSample, double expressionFoldNumber,
			String geneSequence, Samples sample, ArrayList<Project> projects) {
		setExpressionName(name);
		setDate(date);
		setHasSample(hasSample);
		setExpressionFoldNumber(expressionFoldNumber);
		setGeneSequence(geneSequence);
		setSample(sample);
		setProjects(projects);
	}
}
