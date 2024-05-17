package com.example.demo.service;

import com.example.demo.model.Hotel;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room;
import com.example.demo.repository.IReservationRepository;
import com.example.demo.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    IRoomRepository iRoomRepository;

    @Autowired
    IReservationRepository reservationRepository;

    public List<Room> getAll(){
        return iRoomRepository.findAll();
    }

    public List<Room> getByHotelId(int id){
        return iRoomRepository.findByHotelId(id);
    }

    public Room getByRoomNumberAndHotel(int roomNumber, Hotel hotel){
        return iRoomRepository.findByRoomNumberAndHotel(roomNumber, hotel);
    }

    public void save(Room room){
        iRoomRepository.save(room);
    }

    public void updateAllRoomAvailability() {
        List<Room> rooms = getAll();
        LocalDateTime currentTime = LocalDateTime.now();

        for (Room room : rooms) {
            boolean isRoomAvailable = true;
            List<Reservation> reservations = reservationRepository.findByRoomId(room.getId());

            for (Reservation reservation : reservations) {
                if (currentTime.isAfter(reservation.getStartDate()) && currentTime.isBefore(reservation.getEndDate())) {
                    isRoomAvailable = false;
                    break;
                }
            }

            room.setIsAvailable(isRoomAvailable);
            save(room);
        }
    }

    public void updateRoomAvailability(int roomId) {
        Room room = iRoomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        boolean isRoomAvailable = true;
        List<Reservation> reservations = reservationRepository.findByRoomId(roomId);

        for (Reservation reservation : reservations) {
            if (currentTime.isAfter(reservation.getStartDate()) && currentTime.isBefore(reservation.getEndDate())) {
                isRoomAvailable = false;
                break;
            }
        }

        room.setIsAvailable(isRoomAvailable);
        save(room);
    }
}
