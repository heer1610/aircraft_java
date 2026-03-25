package com.student.aircraft.controller;

import com.student.aircraft.model.Airline;
import com.student.aircraft.service.AirlineService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@CrossOrigin(origins = "*")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping
    public ResponseEntity<Airline> create(@Valid @RequestBody Airline airline) {
        return ResponseEntity.status(HttpStatus.CREATED).body(airlineService.create(airline));
    }

    @GetMapping
    public ResponseEntity<List<Airline>> getAll() { return ResponseEntity.ok(airlineService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<Airline> getById(@PathVariable Long id) { return ResponseEntity.ok(airlineService.findById(id)); }

    @PutMapping("/{id}")
    public ResponseEntity<Airline> update(@PathVariable Long id, @Valid @RequestBody Airline airline) {
        return ResponseEntity.ok(airlineService.update(id, airline));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        airlineService.delete(id);
        return ResponseEntity.ok("Airline deleted successfully");
    }
}
