package com.student.aircraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Registration number is required")
    @Column(unique = true, nullable = false, length = 20)
    private String registrationNumber;

    @NotBlank(message = "Model is required")
    @Column(nullable = false, length = 100)
    private String model;

    @NotBlank(message = "Manufacturer is required")
    @Column(nullable = false, length = 100)
    private String manufacturer;

    @Min(value = 1900, message = "Year must be after 1900")
    private Integer yearOfManufacture;

    @NotBlank(message = "Status is required")
    @Column(nullable = false, length = 20)
    private String status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<MaintenanceRecord> maintenanceRecords = new ArrayList<>();

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public Aircraft() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String v) { this.registrationNumber = v; }
    public String getModel() { return model; }
    public void setModel(String v) { this.model = v; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String v) { this.manufacturer = v; }
    public Integer getYearOfManufacture() { return yearOfManufacture; }
    public void setYearOfManufacture(Integer v) { this.yearOfManufacture = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public Airline getAirline() { return airline; }
    public void setAirline(Airline v) { this.airline = v; }
    public List<MaintenanceRecord> getMaintenanceRecords() { return maintenanceRecords; }
    public void setMaintenanceRecords(List<MaintenanceRecord> v) { this.maintenanceRecords = v; }
}
