package com.student.aircraft.service;

import com.student.aircraft.exception.ResourceNotFoundException;
import com.student.aircraft.model.Airline;
import com.student.aircraft.repository.AirlineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public Airline create(Airline airline) {
        if (airlineRepository.existsByName(airline.getName()))
            throw new RuntimeException("Airline name already exists");
        if (airlineRepository.existsByIataCode(airline.getIataCode()))
            throw new RuntimeException("IATA code already exists");
        return airlineRepository.save(airline);
    }

    public List<Airline> findAll() { return airlineRepository.findAll(); }

    public Airline findById(Long id) {
        return airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id: " + id));
    }

    @Transactional
    public Airline update(Long id, Airline details) {
        Airline airline = findById(id);
        airline.setName(details.getName());
        airline.setIataCode(details.getIataCode());
        airline.setCountry(details.getCountry());
        airline.setDescription(details.getDescription());
        return airlineRepository.save(airline);
    }

    public void delete(Long id) {
        findById(id);
        airlineRepository.deleteById(id);
    }
}
