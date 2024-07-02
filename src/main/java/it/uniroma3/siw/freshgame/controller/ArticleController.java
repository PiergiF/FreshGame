package it.uniroma3.siw.freshgame.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.validator.cfg.defs.pl.REGONDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.freshgame.controller.validator.ArticleValidator;
import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Credentials;
import it.uniroma3.siw.freshgame.model.Genres;
import it.uniroma3.siw.freshgame.model.Journalist;
import it.uniroma3.siw.freshgame.model.Platforms;
import it.uniroma3.siw.freshgame.model.Review;
import it.uniroma3.siw.freshgame.model.Tags;
import it.uniroma3.siw.freshgame.service.ArticleService;
import it.uniroma3.siw.freshgame.service.CredentialsService;
import it.uniroma3.siw.freshgame.service.GameService;
import it.uniroma3.siw.freshgame.service.JournalistService;
import it.uniroma3.siw.freshgame.service.ReviewService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class ArticleController {

    /*
    @Autowired
    private JournalistService journalistService;
    */
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleValidator articleValidator;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/all/allArticlesPage")
    public String getAllArticlePage(Model model) {
        model.addAttribute("articles", this.articleService.getAllArticlesOrderedByDateTime());
        //per la sub navbar
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("platforms", Platforms.values());
        return "all/allArticlesPage.html";
    }
    

    @GetMapping("/all/allSpecificTagPage/{tag}")
    public String getAllNewsPage(@PathVariable("tag") Tags tag, Model model) {
        //model.addAttribute("allNews", this.articleService.getAllArticlesByTag(tag));
        List<Article> listTagArticle = this.articleService.getAllArticlesByTag(tag);
        model.addAttribute("allArticlesWithTag", this.articleService.orderSpecificArticleListByDateTime(listTagArticle));
        model.addAttribute("specificTag", tag);
        //per la sub navbar
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("platforms", Platforms.values());
        return "all/allSpecificTagPage.html";
    }

    @GetMapping("/all/articlePage/{id}")
    public String getArticlePage(@PathVariable("id")Long articleId, Model model) {
        Article article = this.articleService.getArticleById(articleId);
        model.addAttribute("article", article);

        for (Tags tag : article.getTags()) {
            if(tag == Tags.REVIEWS){
                model.addAttribute("isReview", true);
                Journalist j = article.getJournalist();
                for(Review r : j.getReviews()){
                    if(r.getGame().equals(article.getGame())){
                        Float value = r.getValue();
                        model.addAttribute("reviewValue", value);
                        if(value == 0){
                            model.addAttribute("reviewValueIsZero", true);
                        }
                    }
                }
            }
        }

        //per la sub navbar
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("platforms", Platforms.values());
        return "all/articlePage.html";
    }
    
    

    @GetMapping("/journalist/createArticlePage")
    public String getAddRecipePage(Model model) {
        model.addAttribute("newArticle", new Article());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("games", this.gameService.getAllGames());
        model.addAttribute("date", LocalDate.now());

        //ottenere il giornalista
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        model.addAttribute("journalist", credentials.getJournalist());
        return "journalist/createArticlePage.html";
    }

    @PostMapping("/journalist/articleData")
    public String createArticle(@Valid @ModelAttribute Article article, BindingResult bindingResultArticle,
                                @RequestParam("date") LocalDate date,
                                @RequestParam(required = false, name = "reviewValue") Float reviewValue,
                                @RequestParam("images") MultipartFile[] files)
    {
        
        this.articleValidator.validate(article, bindingResultArticle);

        if(bindingResultArticle.hasErrors()){
          //return "redirect:/journalist/createArticlePage.html?error=true";
          return "redirect:/journalist/createArticlePage?error=true"; 
        }else{
            try {
                List<String> fotosBase64 = new ArrayList<>();
                for(MultipartFile file : files){
                    byte[] byteFoto = file.getBytes();
                    fotosBase64.add(Base64.getEncoder().encodeToString(byteFoto));
                }
                article.setImagesBase64(fotosBase64);
            }catch (IOException e) {
                //model.addAttribute("message", "Recipe upload failed!");
                return "redirect:/journalist/createArticlePage.html?errorImage=true";
            }
            
            article.setDateTime(LocalDateTime.now());

            if(reviewValue != null){
                Review review = new Review();
                Journalist journalist = article.getJournalist();
                review.setValue(reviewValue);
                review.setGame(article.getGame());
                review.setJournalist(journalist);
                this.reviewService.save(review);
                //List<Review> reviewsJournalist = journalist.getReviews();
                //reviewsJournalist.add(review);
            }

            //article.setDate(date);
            this.articleService.save(article);
            
            return "redirect:/";
        }
    }
    
    @GetMapping("/journalist/editArticlePage/{id}")
    public String getEditArticlePage(@PathVariable("id") Long articleId, Model model) {
        Article article = this.articleService.getArticleById(articleId);

        if(article.getJournalist().getId() == model.getAttribute("loggedId")){
            model.addAttribute("article", article);
            model.addAttribute("date", LocalDate.now());

            //nel caso sia una recensione
            for (Tags tag : article.getTags()) {
                if(tag == Tags.REVIEWS){
                    model.addAttribute("isReview", true);
                    Journalist j = article.getJournalist();
                    for(Review r : j.getReviews()){
                        if(r.getGame().equals(article.getGame())){
                            Float value = r.getValue();
                            model.addAttribute("reviewValue", value);
                            if(value == 0){
                                model.addAttribute("reviewValueIsZero", true);
                            }
                        }
                    }
                }
            }

            //per la sub navbar
            model.addAttribute("genres", Genres.values());
            model.addAttribute("tags", Tags.values()); //questo mi serve
            model.addAttribute("platforms", Platforms.values());

            return "journalist/editArticlePage.html";
        }
        else{
            return"redirect:/";
        } 
    }

    @PostMapping("/journalist/articleEditData/{id}")
    public String postMethodName(@PathVariable("id")Long articleId, 
                                    @Valid @ModelAttribute Article article, BindingResult bindingResultArticle, Model model,
                                    @RequestParam("newImages") MultipartFile[] newFiles,
                                    @RequestParam(required = false, value = "removeImageIndexes") List<Integer> removeImageIndexes,
                                    @RequestParam(required = false, name = "reviewValue") Float reviewValue,
                                    @RequestParam(required = false, name = "oldReview") Float oldReview)
    {
        if (bindingResultArticle.hasErrors()) {
            return "redirect:/journalist/articleEditData/" + articleId;
        }

        Article existingArticle = this.articleService.getArticleById(articleId);
        if (existingArticle == null) {
            // Gestisce il caso in cui l'articolo non esiste
            return "redirect:/errorPage";
        }
        if(existingArticle.getJournalist().getId() == model.getAttribute("loggedId")){
            existingArticle.setTitle(article.getTitle());
            existingArticle.setBody(article.getBody());
            existingArticle.setIntroduction(article.getIntroduction());
            existingArticle.setTags(article.getTags());

            //rimuovi immagini
            if(removeImageIndexes != null){
                for(int index : removeImageIndexes){
                    existingArticle.getImagesBase64().remove(index);
                }
            }

            //aggiungi immagini
            if(newFiles != null){
                for(MultipartFile newFile : newFiles){
                    if(!newFile.isEmpty()){
                        try{
                            byte[] byteFoto = newFile.getBytes();
                            existingArticle.getImagesBase64().add(Base64.getEncoder().encodeToString(byteFoto));
                        }catch(IOException e){
                            //model.addAttribute("message", "Recipe upload failed!");
                            return "chef_admin/addRecipePage.html";
                        }
                    }
                }
            }


            if(reviewValue != null){
                Journalist journalist = article.getJournalist();
                List<Review> allReview = journalist.getReviews();
                Review r;
                boolean change = false;
                for(int i=0;i<allReview.size();i++){
                    r = allReview.get(i);
                    if(r.getGame().equals(article.getGame())){
                        allReview.get(i).setValue(reviewValue);
                        this.reviewService.save(allReview.get(i));
                        change = true;
                    }
                }
                if(!change){
                    Review review = new Review();
                    review.setValue(reviewValue);
                    review.setGame(article.getGame());
                    review.setJournalist(journalist);
                    this.reviewService.save(review);
                }
            }

            this.articleService.save(existingArticle);

            return "redirect:/all/articlePage/" + articleId;

        }else{
            return "/";
        }
    }
    

}
