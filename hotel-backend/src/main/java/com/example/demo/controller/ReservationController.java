package com.example.demo.controller;

import com.example.demo.model.Reservation;
import com.example.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping("/room")
    public List<Reservation> getByRoomId(@RequestParam int id){
        return  reservationService.getByRoomId(id);
    }

    @DeleteMapping("/id")
    public void deleteById(@RequestParam int id){
        reservationService.deleteById(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Reservation reservation){
        reservationService.save(reservation);
    }
}
