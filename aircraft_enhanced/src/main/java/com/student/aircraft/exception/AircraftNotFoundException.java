package com.student.aircraft.exception;

/** Kept for backward compatibility; prefer ResourceNotFoundException */
public class AircraftNotFoundException extends ResourceNotFoundException {
    public AircraftNotFoundException(String message) { super(message); }
}
