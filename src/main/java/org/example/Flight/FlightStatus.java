package org.example.Flight;

public enum FlightStatus {
    SCHEDULED("Flight is scheduled"),
    DELAYED("Flight is delayed"),
    BOARDING("Boarding in progress"),
    DEPARTED("Flight has departed"),
    ARRIVED("Flight has arrived"),
    CANCELLED("Flight is cancelled"),
    ON_TIME("Flight is on time"),
    IN_FLIGHT("Flight is in the air");

    private final String description;

    FlightStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
