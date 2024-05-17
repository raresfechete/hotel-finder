package com.example.demo.controller;


import com.example.demo.model.Room;
import com.example.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/room")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/all")
    public List<Room> getAll(){
        return roomService.getAll();
    }

    @GetMapping("/hotel")
    public List<Room> getByHotel(@RequestParam int id){
        return roomService.getByHotelId(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Room room){
        roomService.save(room);
    }
}
