package it.uniroma3.siw.freshgame.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.freshgame.controller.validator.GameValidator;
import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Credentials;
import it.uniroma3.siw.freshgame.model.Game;
import it.uniroma3.siw.freshgame.model.Genres;
import it.uniroma3.siw.freshgame.model.Journalist;
import it.uniroma3.siw.freshgame.model.Platforms;
import it.uniroma3.siw.freshgame.model.Reader;
import it.uniroma3.siw.freshgame.model.Review;
import it.uniroma3.siw.freshgame.model.Tags;
import it.uniroma3.siw.freshgame.service.ArticleService;
import it.uniroma3.siw.freshgame.service.CredentialsService;
import it.uniroma3.siw.freshgame.service.GameService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameValidator gameValidator;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/all/allGamesPage")
    public String getAllArticlePage(Model model) {
        model.addAttribute("games", this.gameService.getAllGamesOrderedByDate());
        //per la sub navbar
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("platforms", Platforms.values());
        return "all/allGamesPage.html";
    }

    @GetMapping("/all/gamePage/{id}")
    public String getGamePage(@PathVariable("id")Long gameId, Model model) {
        Game game = this.gameService.getGameById(gameId);
        model.addAttribute("game", game);
        //per la sub navbar
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("platforms", Platforms.values());

        UserDetails userDetails = GlobalController.getUserDetails();
        Reader loggedReader = null;
        Journalist loggedJournalist = null;
        if(userDetails!=null){
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            if(credentials.getRole().equals("READER")){
                loggedReader = credentials.getReader();
                model.addAttribute("userLogged", "reader");
            }
            if(credentials.getRole().equals("JOURNALIST")){
                loggedJournalist = credentials.getJournalist();
                model.addAttribute("userLogged", "journalist");
            }
        }

        float sumReaderReviewsValue = 0;
        int numReaderReviews = 0;
        float averageReaderReviewsValue;

        float sumJournalistReviewsValue = 0;
        int numJournalistReviews = 0;
        float averageJournalistReviewsValue;

        boolean loggedHaveReview = false;

        for(Review r : game.getReviews()){

            if(r.getReader() != null){
                sumReaderReviewsValue += r.getValue();
                numReaderReviews++;
                if(loggedReader!=null && r.getReader().getId() == loggedReader.getId() && userDetails!=null){
                    model.addAttribute("loggedReaderReview", r);
                    model.addAttribute("createReview", false);
                    loggedHaveReview = true;
                }
            }else if(r.getJournalist() != null){
                sumJournalistReviewsValue += r.getValue();
                numJournalistReviews++;
                if(loggedJournalist!=null && r.getJournalist().equals(loggedJournalist) && userDetails!=null){
                    model.addAttribute("loggedJournalistReview", r);
                    model.addAttribute("createReview", false);
                    loggedHaveReview = true;
                }
            }


            /*
            if(r.getJournalist() != null){
                Review journalistReview = r;
                model.addAttribute("journalistReview", journalistReview);
                return "all/gamePage.html";
            }
            
            if(loggedReader != null){
                if(r.getReader().getId() == loggedReader.getId()){
                    model.addAttribute("readerReview", r);
                    return "all/gamePage.html";
                }
            }
            */
            
            //if(r.getReader().equals(loggedReader)){

            //}
        }
        if(numReaderReviews > 0){
            averageReaderReviewsValue = sumReaderReviewsValue / numReaderReviews;
            if(averageReaderReviewsValue == 0){
                //model.addAttribute("averageReaderIsZero", true);
                model.addAttribute("averageReaderIsZero", true);
                model.addAttribute("noReaderAverage", false);
            }else{
                averageReaderReviewsValue =  (float) (Math.floor(averageReaderReviewsValue*10.0)/10.0);
                model.addAttribute("averageReaderReviewsValue", averageReaderReviewsValue);
                //model.addAttribute("averageReaderIsZero", false);
                model.addAttribute("averageReaderIsZero", false);
                model.addAttribute("noReaderAverage", false);
            }
        }else{
            model.addAttribute("noReaderAverage", true);
        }
        if(numJournalistReviews > 0 ){
            averageJournalistReviewsValue = sumJournalistReviewsValue / numJournalistReviews;
            if(averageJournalistReviewsValue == 0){
                //model.addAttribute("averageJournalistIsZero", true);
                model.addAttribute("averageJournalistIsZero", true);
                model.addAttribute("noJournalistAverage", false);
            }else{
                averageJournalistReviewsValue =  (float) (Math.floor(averageJournalistReviewsValue*10.0)/10.0);
                model.addAttribute("averageJournalistReviewsValue", averageJournalistReviewsValue);
                //model.addAttribute("averageJournalistIsZero", false);
                model.addAttribute("averageJournalistIsZero", false);
                model.addAttribute("noJournalistAverage", false);
            }
        }
        else{
            model.addAttribute("noJournalistAverage", true);
        }
        model.addAttribute("numReaderReviews", numReaderReviews);
        model.addAttribute("numJournalistReviews", numJournalistReviews);

        if(!loggedHaveReview){
            model.addAttribute("newReview", new Review());
            model.addAttribute("createReview", true);
        }

        //tutti gli articoli sul gioco
        List<Article> listArticles = this.articleService.getAllArticlesByGame(game);
        model.addAttribute("allArticleOfGame", this.articleService.orderSpecificArticleListByDateTime(listArticles));

        //recensione del gioco
        Article reviewGameArticle = this.articleService.getReviewArticleOfSpecificGame(game);
        model.addAttribute("reviewArticle", reviewGameArticle);
        
        return "all/gamePage.html";
    }
    

    @GetMapping("/journalist_editor/addGamePage")
    public String getAddGamePage(Model model) {
        model.addAttribute("newGame", new Game());
        model.addAttribute("genres", Genres.values());
        model.addAttribute("platforms", Platforms.values());
        //model.addAttribute("games", this.gameService.getAllGames());
        //model.addAttribute("date", LocalDate.now());

        
        return "journalist_editor/addGamePage.html";
    }

    @PostMapping("/journalist_editor/gameData")
    public String createArticle(@Valid @ModelAttribute Game game, BindingResult bindingResultGame,
                                //@RequestParam("date") LocalDate date,
                                @RequestParam("images") MultipartFile[] files, Model model)
    {
        this.gameValidator.validate(game, bindingResultGame);
        if(bindingResultGame.hasErrors()){
            /*
            List<ObjectError> e = bindingResultGame.getAllErrors();
            for (ObjectError objectError : e) {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAA" + e.toString());
            }
            if(bindingResultGame.hasFieldErrors("game.duplicate")){
                model.addAttribute("duplicatedGame", true);
                return "redirect:/journalist_editor/addGamePage?duplicatedGame=true";
            }else{
                return "redirect:/journalist_editor/addGamePage?error=true";
            }*/
          //return "redirect:/journalist_editor/addGamePage.html?error=true";
          return "redirect:/journalist_editor/addGamePage?error=true"; //funziona
        }else{
            try {
                List<String> fotosBase64 = new ArrayList<>();
                for(MultipartFile file : files){
                    byte[] byteFoto = file.getBytes();
                    fotosBase64.add(Base64.getEncoder().encodeToString(byteFoto));
                }
                game.setImagesBase64(fotosBase64);
            }catch (IOException e) {
                //model.addAttribute("message", "Recipe upload failed!");
                return "redirect:/journalist_editor/addGamePage.html?errorImage=true";
            }
            
            //article.setDateTime(LocalDateTime.now());
            //article.setDate(date);
            this.gameService.save(game);
            
            return "redirect:/all/gamePage/" + game.getId();
        }
    }

    @GetMapping("/all/platformPage/{platform}")
    public String getPlatformPage(@PathVariable("platform") Platforms platform, Model model) {
        model.addAttribute("games", this.gameService.getAllGamesByPlatform(platform));
        model.addAttribute("platform", platform);
        //per la sub navbar
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("platforms", Platforms.values());
        return "all/platformPage.html";
    }

    @GetMapping("/all/genrePage/{genre}")
    public String getGenrePage(@PathVariable("genre") Genres genre, Model model) {
        model.addAttribute("games", this.gameService.getAllGamesByGenre(genre));
        model.addAttribute("genre", genre);
        //per la sub navbar
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("platforms", Platforms.values());
        return "all/genrePage.html";
    }
    

}
