package com.example.demo.repository;

import com.example.demo.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByRoomId(int id);
}
