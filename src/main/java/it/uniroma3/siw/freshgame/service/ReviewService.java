package it.uniroma3.siw.freshgame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Journalist;
import it.uniroma3.siw.freshgame.model.Review;
import it.uniroma3.siw.freshgame.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews(){
        return this.reviewRepository.findAll();
    }

    public Review getReviewById(Long id){
        return this.reviewRepository.findById(id).orElse(null);
    }

    public Review save(Review review){
        return this.reviewRepository.save(review);
    }

    public void deleteById(Long id){
        this.reviewRepository.deleteById(id);
    }

    public List<Review> getAllReviewsByJournalist (Journalist journalist){
        List<Review> allReviews = this.getAllReviews();
        List<Review> journalistReviews = new ArrayList<>();
        for(Review r : allReviews){
            if(r.getJournalist() != null){
                if(r.getJournalist().equals(journalist)){
                    journalistReviews.add(r);
                }
            }
        }
        return journalistReviews;
    }

    public void deleteReviewsByJournalist(Journalist journalist){
        List<Review> reviews = this.getAllReviewsByJournalist(journalist);
        for(Review review : reviews){
            this.deleteById(review.getId());
        }
    }
}
