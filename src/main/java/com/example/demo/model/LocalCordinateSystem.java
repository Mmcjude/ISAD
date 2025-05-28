package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LocalCordinateSystem")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LocalCordinateSystem {
	@Column(name = "LcsId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private long lcsId;
	
	@Column(name = "FieldNumber")
	@Min(value = 1, message = "Field number must be at least 1.")
	private int fieldNumber;
	
	@Column(name = "Row")
	@Min(value = 1, message = "Row number must be at least 1.")
	private int row;

	@Column(name = "Number")
	@Min(value = 1, message = "Number must be at least 1.")
	private int number;
	
	@OneToOne(mappedBy = "localLocation")
	@ToString.Exclude
	private GeographicInformationSystem gis;
	
	public LocalCordinateSystem(int inputFieldNumber, int inputRow, int inputNumber) {
		setFieldNumber(inputFieldNumber);
		setRow(inputRow);
		setNumber(inputNumber);
	}
}
