package com.musala.drone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.musala.drone.model.Drone;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

	Drone findBySerialNumber(String serialNumber);

	@Query("SELECT u FROM Drone u WHERE u.batteryCapacity >= 25 and u.state =0 ")
	List<Drone> getAvailableDrones();

}