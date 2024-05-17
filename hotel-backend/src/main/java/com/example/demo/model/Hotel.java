package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hotel {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @OneToMany(mappedBy = "hotel")
    @JsonBackReference("hotel-rooms") //circular json serialization avoidance
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel")
    @JsonBackReference("hotel-reviews") //circular json serialization avoidance
    private List<Review> reviews;
}
