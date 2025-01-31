package org.example.Airport.airport.Enums;

public enum AirportSize {
    LARGE("Large", "More than 1% of annual passenger boardings"),
    MEDIUM("Medium", "0.25% to 1% of annual passenger boardings"),
    SMALL("Small", "0.05% to 0.25% of annual passenger boardings");

    private final String category;
    private final String description;

    AirportSize(String category, String description){
        this.category = category;
        this.description = description;
    }

    public String getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }

}
