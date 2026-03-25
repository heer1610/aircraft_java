package com.student.aircraft.service;

import com.student.aircraft.exception.ResourceNotFoundException;
import com.student.aircraft.model.Aircraft;
import com.student.aircraft.repository.AircraftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;

    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    public Aircraft create(Aircraft aircraft) {
        if (aircraftRepository.existsByRegistrationNumber(aircraft.getRegistrationNumber()))
            throw new RuntimeException("Registration number already exists: " + aircraft.getRegistrationNumber());
        return aircraftRepository.save(aircraft);
    }

    public List<Aircraft> findAll() { return aircraftRepository.findAll(); }

    public Aircraft findById(Long id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id: " + id));
    }

    public List<Aircraft> findByStatus(String status) { return aircraftRepository.findByStatus(status); }

    public List<Aircraft> findByAirline(Long airlineId) { return aircraftRepository.findByAirlineId(airlineId); }

    @Transactional
    public Aircraft update(Long id, Aircraft details) {
        Aircraft aircraft = findById(id);
        aircraft.setRegistrationNumber(details.getRegistrationNumber());
        aircraft.setModel(details.getModel());
        aircraft.setManufacturer(details.getManufacturer());
        aircraft.setYearOfManufacture(details.getYearOfManufacture());
        aircraft.setStatus(details.getStatus());
        if (details.getAirline() != null) aircraft.setAirline(details.getAirline());
        return aircraftRepository.save(aircraft);
    }

    public void delete(Long id) {
        findById(id);
        aircraftRepository.deleteById(id);
    }
}
