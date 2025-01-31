package org.example.Flight.Exceptions;

public class InvalidFlightSeatException extends RuntimeException {
    public InvalidFlightSeatException(String message) {
        super(message);
    }
}
