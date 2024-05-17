package com.example.demo.controller;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.model.Review;
import com.example.demo.service.HotelService;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/review")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    HotelService hotelService;

    @GetMapping("/hotel")
    public List<Review> getByHotel(@RequestParam int id){
        return reviewService.getByHotelId(id);
    }

    @GetMapping("/stars")
    public double getStarsAverage(@RequestParam int id){
        return reviewService.getStarsAverage(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody ReviewDTO reviewDTO) {

        Review review = new Review();
        review.setHotel(hotelService.getById(reviewDTO.getHotelId())); // Retrieve Hotel entity
        review.setStars(reviewDTO.getStars());
        review.setMessage(reviewDTO.getMessage());


        reviewService.save(review);
    }
}
