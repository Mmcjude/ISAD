package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Project")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Project {
	@Column(name = "ProjectId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long projectId;
	
	@Column(name = "ProjectNr")
	@Min(1)
	private int projectNr;
	
	@Column(name = "Title")
	@NotNull
	@Pattern(regexp = "[A-ZĀ-Ža-zā-ž0-9/&',.?! ]{3,90}")
	private String title;

	@Column(name = "description")
	@Lob
	@Size(max = 2000)
	private String description;
	
}
