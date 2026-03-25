package com.student.aircraft.controller;

import com.student.aircraft.model.Aircraft;
import com.student.aircraft.service.AircraftService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@CrossOrigin(origins = "*")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping
    public ResponseEntity<Aircraft> create(@Valid @RequestBody Aircraft aircraft) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraftService.create(aircraft));
    }

    @GetMapping
    public ResponseEntity<List<Aircraft>> getAll() { return ResponseEntity.ok(aircraftService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getById(@PathVariable Long id) { return ResponseEntity.ok(aircraftService.findById(id)); }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Aircraft>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(aircraftService.findByStatus(status));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<Aircraft>> getByAirline(@PathVariable Long airlineId) {
        return ResponseEntity.ok(aircraftService.findByAirline(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> update(@PathVariable Long id, @Valid @RequestBody Aircraft aircraft) {
        return ResponseEntity.ok(aircraftService.update(id, aircraft));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        aircraftService.delete(id);
        return ResponseEntity.ok("Aircraft deleted successfully");
    }
}
