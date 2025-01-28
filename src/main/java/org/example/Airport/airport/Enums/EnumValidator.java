package org.example.Airport.airport.Enums;

public class EnumValidator {
    public static boolean isValidAirportType(String type){
        try{
            AirportType.valueOf(type.toUpperCase());
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    public static boolean isValidAirportSize(String size){
        try{
            AirportSize.valueOf(size.toUpperCase());
            return true;
    } catch (IllegalArgumentException e){
        return false;
        }
    }

    public static boolean isValidRegionType(String region){
        try{
            RegionType.valueOf(region.toUpperCase());
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
}
