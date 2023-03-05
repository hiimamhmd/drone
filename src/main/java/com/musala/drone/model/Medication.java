package com.musala.drone.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "MEDICATION")
public class Medication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Pattern(regexp = "[A-Za-z0-9\\-\\_]+", message = "Name Must be  letters, numbers, ‘-‘, ‘_’ ")
	@Column(name = "NAME", nullable = false)
	private String name;

	@Pattern(regexp = "[A-Z-0-9-_]+", message = "Code Must be upper case letters, underscore and numbers ")
	@Column(name = "CODE", nullable = false)
	private String code;

	@Column(name = "WEIGHT", nullable = false)
	private BigDecimal weight;
}
