package it.uniroma3.siw.freshgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Review;
import it.uniroma3.siw.freshgame.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews(){
        return this.reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id){
        return this.reviewRepository.findById(id);
    }
}
