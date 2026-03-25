package com.student.aircraft.controller;

import com.student.aircraft.model.MaintenanceRecord;
import com.student.aircraft.service.MaintenanceRecordService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(origins = "*")
public class MaintenanceRecordController {

    private final MaintenanceRecordService service;

    public MaintenanceRecordController(MaintenanceRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MaintenanceRecord> create(@Valid @RequestBody MaintenanceRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(record));
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRecord>> getAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRecord> getById(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }

    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<List<MaintenanceRecord>> getByAircraft(@PathVariable Long aircraftId) {
        return ResponseEntity.ok(service.findByAircraft(aircraftId));
    }

    @GetMapping("/technician/{userId}")
    public ResponseEntity<List<MaintenanceRecord>> getByTechnician(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findByTechnician(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceRecord> update(@PathVariable Long id, @Valid @RequestBody MaintenanceRecord record) {
        return ResponseEntity.ok(service.update(id, record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Maintenance record deleted");
    }
}
