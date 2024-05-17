package com.example.demo.service;


import com.example.demo.model.Review;
import com.example.demo.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    IReviewRepository iReviewRepository;

    public List<Review> getByHotelId(int id){
        return iReviewRepository.findByHotelId(id);
    }

    public double getStarsAverage(int id){
        List<Review> reviews = this.getByHotelId(id);
        double score =0.0;

        for(Review review: reviews)
            score += review.getStars();

        if (!reviews.isEmpty())
            score /= reviews.size();

        return score;
    }

    public void save(Review review){
        iReviewRepository.save(review);
    }

}
