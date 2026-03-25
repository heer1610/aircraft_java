package com.student.aircraft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_records")
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aircraft_id", nullable = false)
    @JsonIgnoreProperties("maintenanceRecords")
    private Aircraft aircraft;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technician_id")
    @JsonIgnoreProperties({"maintenanceRecords", "roles", "password"})
    private User technician;

    @NotBlank(message = "Maintenance type is required")
    @Column(nullable = false, length = 50)
    private String maintenanceType;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be 10-500 characters")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private LocalDateTime maintenanceDate;
    private LocalDateTime completionDate;

    @PositiveOrZero(message = "Cost must be >= 0")
    private Double cost;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime recordedAt;

    @PrePersist
    protected void onCreate() { recordedAt = LocalDateTime.now(); }

    public MaintenanceRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Aircraft getAircraft() { return aircraft; }
    public void setAircraft(Aircraft v) { this.aircraft = v; }
    public User getTechnician() { return technician; }
    public void setTechnician(User v) { this.technician = v; }
    public String getMaintenanceType() { return maintenanceType; }
    public void setMaintenanceType(String v) { this.maintenanceType = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public LocalDateTime getMaintenanceDate() { return maintenanceDate; }
    public void setMaintenanceDate(LocalDateTime v) { this.maintenanceDate = v; }
    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime v) { this.completionDate = v; }
    public Double getCost() { return cost; }
    public void setCost(Double v) { this.cost = v; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime v) { this.recordedAt = v; }
}
