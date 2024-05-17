package com.example.demo.service;

import com.example.demo.model.Reservation;
import com.example.demo.repository.IReservationRepository;
import com.example.demo.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    IReservationRepository iReservationRepository;

    @Autowired
    RoomService roomService;

    public List<Reservation> getByRoomId(int id) {
        return iReservationRepository.findByRoomId(id);
    }

    public boolean isReservationAvailable(Reservation newReservation) {
        List<Reservation> existingReservations = getByRoomId(newReservation.getRoomId());


        for (Reservation reservation : existingReservations) {
            if (newReservation.getEndDate().isAfter(reservation.getStartDate()) && newReservation.getStartDate().isBefore(reservation.getEndDate())) {
                return false;
            }
        }

        return true;
    }

    public void deleteById(int id){
        iReservationRepository.deleteById(id);
        roomService.updateAllRoomAvailability();
    }

    public void save(Reservation reservation) {
        if (isReservationAvailable(reservation)) {
            iReservationRepository.save(reservation);
            roomService.updateRoomAvailability(reservation.getRoomId());
        } else {
            throw new RuntimeException("Reservation conflicts with existing bookings.");
        }
    }
}
