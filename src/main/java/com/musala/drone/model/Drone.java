package com.musala.drone.model;

import java.util.HashSet;
import java.util.Set;

import com.musala.drone.util.DroneModel;
import com.musala.drone.util.DroneState;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "DRONE")
public class Drone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 100, message = "serial number cann`t be greater than 100 char")
	@NotNull(message = "serial number is mandatory")
	@Column(name = "SERIAL_NUMBER", nullable = false, length = 100, unique = true)
	private String serialNumber;

	@NotNull(message = "model is mandatory")
	@Column(name = "MODEL", nullable = false)
	private DroneModel model;

	@NotNull(message = "weight limit is mandatory")
	@Max(500)
	@Column(name = "WEIGHT_LIMIT", nullable = false)
	private Double weightLimit;

	@NotNull(message = "battery capacity is mandatory")
	@Max(100)
	@Column(name = "BATTERY_CAPACITY", nullable = false)
	private Double batteryCapacity;

	@NotNull(message = "Drone State is mandatory")
	@Column(name = "STATE", nullable = false)
	private DroneState state;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "drone_medications", joinColumns = @JoinColumn(name = "drone_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "id"))
	private Set<Medication> medications = new HashSet<>();

	public void addMedication(Set<Medication> medication) {
		this.medications.addAll(medication);
	}

}
