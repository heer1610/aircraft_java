package com.student.aircraft.repository;

import com.student.aircraft.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    boolean existsByRegistrationNumber(String registrationNumber);
    List<Aircraft> findByStatus(String status);
    List<Aircraft> findByAirlineId(Long airlineId);
}
