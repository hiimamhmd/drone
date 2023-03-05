package com.musala.drone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musala.drone.model.DroneHistory;

@Repository
public interface DroneHistoryRepository extends JpaRepository<DroneHistory, Long> {

}