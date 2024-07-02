package it.uniroma3.siw.freshgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.freshgame.model.Credentials;
import it.uniroma3.siw.freshgame.model.Journalist;
import it.uniroma3.siw.freshgame.model.Reader;
import it.uniroma3.siw.freshgame.model.Review;
import it.uniroma3.siw.freshgame.service.CredentialsService;
import it.uniroma3.siw.freshgame.service.GameService;
import it.uniroma3.siw.freshgame.service.ReviewService;


@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private GameService gameService;

    @PostMapping("/logged/reviewData/{gameId}")
    public String postMethodName(@ModelAttribute Review review, @PathVariable("gameId") Long gameId,
                                @RequestParam("updateReview") boolean updateReview, @RequestParam( required = false, name = "updateReviewId") Long updateReviewId , Model model)
    {
        //se la recensione già esiste ed è una modifica
        if(updateReview && updateReviewId != null){
            Review existReview = this.reviewService.getReviewById(updateReviewId);
            existReview.setValue(review.getValue());
            existReview.setGame(this.gameService.getGameById(gameId));
            this.reviewService.save(existReview);
        }
        //se la recensione è nuova
        else{
            UserDetails userDetails = GlobalController.getUserDetails();
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            String role = (String) model.getAttribute("accountRole");
            if(role.equals("READER")){
                Reader reader = credentials.getReader();
                review.setReader(reader);
                //return credentials.getReader().getId();
            }else if(role.equals("JOURNALIST")){
                Journalist journalist = credentials.getJournalist();
                review.setJournalist(journalist);
                //return credentials.getJournalist().getId();
            }

            review.setGame(this.gameService.getGameById(gameId));
            this.reviewService.save(review);
        }
        return "redirect:/all/gamePage/" + gameId;
    }
    

}
