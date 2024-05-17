package com.example.demo.service;

import com.example.demo.model.Coordinates;
import com.example.demo.model.Hotel;
import com.example.demo.repository.IHotelRepository;
import com.example.demo.util.DistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    IHotelRepository iHotelRepository;

    @Autowired
    DistanceCalculator distanceCalculator;

    public List<Hotel> getAll(){
        return iHotelRepository.findAll();
    }

    public Hotel getById(int id){
        return iHotelRepository.findById(id).orElse(null);
    }

    public List<Hotel> getWithin(Coordinates coordinates, double distance){
        List<Hotel> hotels = this.getAll();
        List<Hotel> hotelsWithinDistance = new ArrayList<Hotel>();

        for (Hotel hotel : hotels){
            Coordinates hotelCoordinates = new Coordinates(hotel.getLatitude(), hotel.getLongitude());
            if(distanceCalculator.isWithinDistance(coordinates, hotelCoordinates, distance)){
                hotelsWithinDistance.add(hotel);
            }
        }

        return hotelsWithinDistance;
    }

    public void save(Hotel hotel){
        iHotelRepository.save(hotel);
    }

}
