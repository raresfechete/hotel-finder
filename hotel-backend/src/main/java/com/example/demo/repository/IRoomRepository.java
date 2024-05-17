package com.example.demo.repository;

import com.example.demo.model.Hotel;
import com.example.demo.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByHotel(Hotel hotel);
    Room findByRoomNumberAndHotel(int number, Hotel hotel);

    List<Room> findByHotelId(int id);
}
