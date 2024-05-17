package com.example.demo.repository;

import com.example.demo.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelRepository  extends JpaRepository<Hotel, Integer> {

}
