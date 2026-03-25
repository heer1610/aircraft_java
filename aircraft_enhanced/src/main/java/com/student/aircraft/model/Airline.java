package com.student.aircraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airlines")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Airline name is required")
    @Column(unique = true, nullable = false, length = 150)
    private String name;

    @NotBlank(message = "IATA code is required")
    @Size(min = 2, max = 3, message = "IATA code must be 2-3 characters")
    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;

    @Column(length = 100)
    private String country;

    @Column(length = 300)
    private String description;

    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Aircraft> aircraft = new ArrayList<>();

    public Airline() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getIataCode() { return iataCode; }
    public void setIataCode(String v) { this.iataCode = v; }
    public String getCountry() { return country; }
    public void setCountry(String v) { this.country = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public List<Aircraft> getAircraft() { return aircraft; }
    public void setAircraft(List<Aircraft> v) { this.aircraft = v; }
}
