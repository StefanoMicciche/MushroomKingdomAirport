package org.example.Airport.airport.Enums;

public enum RegionType {
    EUROPE("EU", "European Region"),
    NORTH_AMERICA("NA", "North American Region"),
    SOUTH_AMERICA("SA", "South American Region"),
    ASIA("AS", "Asian Region"),
    AFRICA("AF", "African Region"),
    OCEANIA("OC", "Oceanian Region"),
    MIDDLE_EAST("ME", "Middle Eastern Region");

    private final String code;
    private final String description;

    RegionType(String code, String description){
        this.code = code;
        this.description = description;
    }

    public String getCode(){
        return code;
    }

    public String getDescription(){
        return description;
    }
}
