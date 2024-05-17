package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnoreProperties("rooms") //circular json serialization avoidance
    private Hotel hotel;

    @Column
    private int roomNumber;

    //1 - single; 2 - double; 3 - suite; 4- matrimonial
    @Column
    private int type;

    @Column
    private int price;

    @Column
    private boolean isAvailable;

    //I think jackson wants these getter/setters spelled like this
    public boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }
}
