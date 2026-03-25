package com.student.aircraft.repository;

import com.student.aircraft.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
    boolean existsByName(String name);
    boolean existsByIataCode(String iataCode);
}
