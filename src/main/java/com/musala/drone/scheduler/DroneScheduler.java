package com.musala.drone.scheduler;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneHistory;
import com.musala.drone.repository.DroneHistoryRepository;
import com.musala.drone.repository.DroneRepository;

@Service
public class DroneScheduler {
	@Autowired
	DroneRepository droneRepository;
	@Autowired
	DroneHistoryRepository droneHistoryRepository;

	@Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
	public void droneBatterySimulator() {
		List<Drone> drones = droneRepository.findAll();
		for (Drone drone : drones) {
			Random r = new Random();
			int low = 0;
			int high = 100;
			Double result = r.nextDouble(high - low) + low;
			drone.setBatteryCapacity(result);
			droneRepository.save(drone);

			DroneHistory dh = DroneHistory.builder().batteryCapacity(result).drone(drone).build();

			droneHistoryRepository.save(dh);
		}
	}

}
