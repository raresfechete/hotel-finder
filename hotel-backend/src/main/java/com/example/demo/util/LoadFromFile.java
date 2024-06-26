package com.example.demo.util;

import com.example.demo.model.Hotel;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room;
import com.example.demo.service.HotelService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class LoadFromFile {
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomService roomService;
    @Autowired
    ReservationService reservationService;

    final String jsonPath = "hotels.json";

    //loads from the json file located in the resources folder
    //very inefficient duplicate protection; doesn't update unless items are deleted
    public void populateFromJsonFile() throws IOException {
        Resource resource = new ClassPathResource(jsonPath);
        File file = resource.getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        Hotel[] hotels = objectMapper.readValue(file, Hotel[].class);

        for (Hotel hotel : hotels) {
            Hotel existingHotel = hotelService.getById(hotel.getId());
            if (existingHotel == null) {
                hotelService.save(hotel);
            } else {
                System.out.println("Hotel with ID " + hotel.getId() + " already exists. Skipping.");
            }


            for (Room room : hotel.getRooms()) {
                //ids are autogenerated for rooms
                Room existingRoom = roomService.getByRoomNumberAndHotel(room.getRoomNumber(), hotel);
                if (existingRoom == null) {
                    room.setHotel(hotel);
                    roomService.save(room);

                    //if room is not available, create a reservation
                    if (!room.getIsAvailable()) {
                        Reservation reservation = new Reservation();

                        reservation.setRoomId(room.getId());
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime startDate = LocalDateTime.of(now.toLocalDate(), LocalTime.of(14, 0)); //today at 14:00
                        LocalDateTime endDate = startDate.plusDays(1).withHour(10); //tomorrow at 10:00

                        reservation.setRoomId(room.getId());
                        reservation.setStartDate(startDate);
                        reservation.setEndDate(endDate);

                        reservationService.save(reservation);
                    }
                } else {
                    System.out.println("Room with room number " + room.getRoomNumber() + " for hotel ID " + hotel.getId() + " already exists. Skipping.");
                }
            }
        }
    }
}
