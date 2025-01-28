package org.example.Flight.Exceptions;

public class InvalidFlightException extends RuntimeException {
    public InvalidFlightException(String message) {
        super(message);
    }
}
