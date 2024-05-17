package com.example.demo.controller;

import com.example.demo.model.Coordinates;
import com.example.demo.model.Hotel;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/hotel")
@CrossOrigin(origins = "*")
public class HotelController {

    @Autowired
    HotelService hotelService;

    @GetMapping("/all")
    public List<Hotel> getAll(){
        return hotelService.getAll();
    }

    @GetMapping("/within")
    public List<Hotel> getWithin(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double distance) {
        Coordinates coordinates = new Coordinates(latitude, longitude);
        return hotelService.getWithin(coordinates, distance);
    }

    @PostMapping("/save")
    public void save(@RequestBody Hotel hotel){
        hotelService.save(hotel);
    }
}
