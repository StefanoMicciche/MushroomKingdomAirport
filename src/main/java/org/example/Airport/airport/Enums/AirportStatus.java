package org.example.Airport.airport.Enums;

public enum AirportStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    UNDER_MAINTEINANCE("Under Maintenance"),
    TEMPORARILY_CLOSED("Temporarily Closed"),
    PERMANENTLY_CLOSED("Permanently Closed");

    private final String displayName;

    AirportStatus(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
