package com.student.aircraft.service;

import com.student.aircraft.exception.ResourceNotFoundException;
import com.student.aircraft.model.MaintenanceRecord;
import com.student.aircraft.repository.MaintenanceRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaintenanceRecordService {

    private final MaintenanceRecordRepository repo;

    public MaintenanceRecordService(MaintenanceRecordRepository repo) {
        this.repo = repo;
    }

    public MaintenanceRecord create(MaintenanceRecord record) { return repo.save(record); }

    public List<MaintenanceRecord> findAll() { return repo.findAll(); }

    public MaintenanceRecord findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found: " + id));
    }

    public List<MaintenanceRecord> findByAircraft(Long aircraftId) {
        return repo.findByAircraftIdOrderByMaintenanceDateDesc(aircraftId);
    }

    public List<MaintenanceRecord> findByTechnician(Long userId) { return repo.findByTechnicianId(userId); }

    @Transactional
    public MaintenanceRecord update(Long id, MaintenanceRecord details) {
        MaintenanceRecord record = findById(id);
        record.setMaintenanceType(details.getMaintenanceType());
        record.setDescription(details.getDescription());
        record.setMaintenanceDate(details.getMaintenanceDate());
        record.setCompletionDate(details.getCompletionDate());
        record.setCost(details.getCost());
        return repo.save(record);
    }

    public void delete(Long id) {
        findById(id);
        repo.deleteById(id);
    }
}
