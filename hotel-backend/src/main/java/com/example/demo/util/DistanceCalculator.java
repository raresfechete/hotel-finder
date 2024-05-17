package com.example.demo.util;

import com.example.demo.model.Coordinates;
import org.springframework.stereotype.Component;

@Component
public class DistanceCalculator {


    public double calculateLatitude(double latitude) {
        double radianValue = Math.toRadians(latitude);
        return latitude*(111132.92 - 559.82 * Math.cos(2 * radianValue) + 1.175 * Math.cos(4 * radianValue) - 0.0023 * Math.cos(6 * radianValue))/1000.0;
    }
    public double calculateLongitude(double longitude) {
        double radianValue = Math.toRadians(longitude);
        return longitude*(111412.84 * Math.cos(radianValue) - 93.5 * Math.cos(3 * radianValue) + 0.118 * Math.cos(5 * radianValue))/1000.0;
    }

    public double calculateDistance(Coordinates userLocation, Coordinates hotelLocation) {
        double xUser = calculateLongitude(userLocation.getLongitude()), yUser = calculateLatitude(userLocation.getLatitude());
        double xHotel = calculateLongitude(hotelLocation.getLongitude()), yHotel = calculateLatitude(hotelLocation.getLatitude());

        return Math.sqrt((xHotel - xUser) * (xHotel - xUser) + (yHotel - yUser) * (yHotel - yUser));
    }

    public boolean isWithinDistance(Coordinates userLocation, Coordinates hotelLocation, double distance) {

        if (calculateDistance(userLocation, hotelLocation) <= distance)
            return true;

        return false;
    }
}
