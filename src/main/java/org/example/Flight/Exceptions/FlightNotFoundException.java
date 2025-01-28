package org.example.Flight.Exceptions;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(String message) {
        super(message);
    }

    public static class InvalidFlightException extends RuntimeException {
        public InvalidFlightException(String message) {
            super(message);
        }
}
}
