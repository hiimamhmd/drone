package com.musala.drone.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musala.drone.model.Drone;
import com.musala.drone.repository.DroneRepository;
import com.musala.drone.util.DataWrapper;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Service
public class DroneService {

	@Autowired
	DroneRepository droneRepository;

	public ResponseEntity<DataWrapper> registerDrone(Drone drone) {

		DataWrapper dw = new DataWrapper();
		dw.setSuccess(false);
		dw.setMessage("Drone Saved Successfully");

		try {
			if (!checkUniqueSerial(drone) && drone.getId() == null)
				throw new Exception("This serial was used before");

			dw.setData(droneRepository.save(drone));
			dw.setSuccess(true);
		} catch (ConstraintViolationException cve) {
			Map<String, String> errors = new HashMap<>();
			cve.getConstraintViolations().forEach((error) -> {
				String errorMessage = error.getMessage();
				errors.put("error", errorMessage);
			});
			dw.setData(errors);
			return new ResponseEntity<DataWrapper>(dw, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			dw.setMessage("an error occurred " + e.getMessage());
		}
		return new ResponseEntity<DataWrapper>(dw, HttpStatus.OK);
	}

	private boolean checkUniqueSerial(@Valid Drone drone) {
		Drone foundDrone = droneRepository.findBySerialNumber(drone.getSerialNumber());
		return (foundDrone == null);
	}

	public ResponseEntity<DataWrapper> loadDrone(Drone drone) {
		DataWrapper dw = new DataWrapper();
		dw.setSuccess(false);

		try {
			Optional<Drone> registeredDrone = droneRepository.findById(drone.getId());
			if (!registeredDrone.isPresent()) {
				dw.setMessage("No Drone With Speceifed ID");
				return new ResponseEntity<DataWrapper>(dw, HttpStatus.OK);
			}

			Drone loadedDrone = registeredDrone.get();

			if (loadedDrone.getBatteryCapacity() < 25) {
				throw new Exception("Drone Battery is Less Than 25% cann`t be loaded");
			}

			if (!checkDroneWeightLimit(drone, loadedDrone)) {
				throw new Exception(
						"Drone Weight Limit is setted to " + loadedDrone.getWeightLimit() + " and cann`t be loaded ");
			}

			loadedDrone.addMedication(drone.getMedications());

			return registerDrone(loadedDrone);
		}

		catch (Exception e) {
			dw.setMessage(e.getMessage());

		}

		return new ResponseEntity<DataWrapper>(dw, HttpStatus.OK);

	}

	public ResponseEntity<DataWrapper> getMedications(Drone drone) {

		DataWrapper dw = new DataWrapper();
		dw.setSuccess(false);

		try {
			Optional<Drone> registeredDrone = droneRepository.findById(drone.getId());
			if (!registeredDrone.isPresent()) {
				dw.setMessage("No Drone With Speceifed ID");
				return new ResponseEntity<DataWrapper>(dw, HttpStatus.OK);
			}
			dw.setData(registeredDrone.get().getMedications());
			dw.setSuccess(true);
		} catch (Exception e) {
			dw.setMessage(e.getMessage());

		}
		return new ResponseEntity<DataWrapper>(dw, HttpStatus.OK);
	}

	public ResponseEntity<DataWrapper> getDroneBattery(Drone drone) {

		DataWrapper dw = new DataWrapper();
		dw.setSuccess(false);

		try {
			Optional<Drone> registeredDrone = droneRepository.findById(drone.getId());
			if (!registeredDrone.isPresent()) {
				dw.setMessage("No Drone With Speceifed ID");
				return new ResponseEntity<DataWrapper>(dw, HttpStatus.OK);
			}
			dw.setData(registeredDrone.get().getBatteryCapacity());
			dw.setSuccess(true);
		} catch (Exception e) {
			dw.setMessage(e.getMessage());

		}
		return new ResponseEntity<DataWrapper>(dw, HttpStatus.OK);

	}

	private boolean checkDroneWeightLimit(Drone drone, Drone loadedDrone) {
		BigDecimal medicationWeightToAdd = drone.getMedications().stream().map(x -> x.getWeight())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal currentLoadedMedications = loadedDrone.getMedications().stream().map(x -> x.getWeight())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal totalMedications = medicationWeightToAdd.add(currentLoadedMedications);

		return totalMedications.doubleValue() <= loadedDrone.getWeightLimit();

	}

	public ResponseEntity<DataWrapper> getAvailableDrones() {
		DataWrapper dw = new DataWrapper();
		dw.setSuccess(false);

		try {

			dw.setData(droneRepository.getAvailableDrones());
			dw.setMessage("");
			dw.setSuccess(true);
		} catch (Exception e) {
			dw.setMessage(e.getMessage());

		}
		return new ResponseEntity<DataWrapper>(dw, HttpStatus.OK);

	}

}
