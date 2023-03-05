package com.musala.drone.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.musala.drone.model.Drone;
import com.musala.drone.service.DroneService;
import com.musala.drone.util.DataWrapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/drone")
public class DroneController {
	@Autowired
	DroneService droneService;
	
	@PostMapping("/register-drone")
	public ResponseEntity<DataWrapper> registerDrone (@Valid @RequestBody Drone drone) {
       
		return droneService.registerDrone(drone);
		
		
	}
	
	
	@PostMapping("/load-drone")
	public ResponseEntity<DataWrapper> loadDrone ( @RequestBody Drone drone) {
		return droneService.loadDrone(drone);
	}
	
	@PostMapping("/get-drone-medications")
	public ResponseEntity<DataWrapper> getDroneMedications ( @RequestBody Drone drone) {
		return droneService.getMedications(drone);
	}
	
	@PostMapping("/get-drone-battery")
	public ResponseEntity<DataWrapper> getDroneBatterys ( @RequestBody Drone drone) {
		return droneService.getDroneBattery(drone);
	}
	
	@PostMapping("/get-available-drones")
	public ResponseEntity<DataWrapper> getAvailableDrones () {
		return droneService.getAvailableDrones();
	}
	
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
}
