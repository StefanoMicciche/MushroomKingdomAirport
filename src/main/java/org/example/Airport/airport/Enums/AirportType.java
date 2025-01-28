package org.example.Airport.airport.Enums;

public enum AirportType {
    INTERNATIONAL("International Airport"),
    DOMESTIC("Domestic Airport"),
    REGIONAL("Regional Airport"),
    PRIVATE("Private Airport"),
    MILITARY("Military Airport"),
    CARGO("Cargo Airport");

    private final String description;

    AirportType(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
