package it.uniroma3.siw.freshgame.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Credentials;
import it.uniroma3.siw.freshgame.model.Tag;
import it.uniroma3.siw.freshgame.service.ArticleService;
import it.uniroma3.siw.freshgame.service.CredentialsService;
import it.uniroma3.siw.freshgame.service.GameService;
import it.uniroma3.siw.freshgame.service.JournalistService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ArticleController {

    /*
    @Autowired
    private JournalistService journalistService;
    */
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private GameService gameService;

    @GetMapping("/all/allNewsPage")
    public String getAllNewsPage(Model model) {
        model.addAttribute("articles", this.articleService.getAllArticles());
        return "all/allNewsPage.html";
    }
    

    @GetMapping("/journalist/createArticlePage")
    public String getAddRecipePage(Model model) {
        model.addAttribute("newArticle", new Article());
        model.addAttribute("tags", Tag.values());
        model.addAttribute("games", this.gameService.getAllGames());
        model.addAttribute("date", LocalDate.now());
        /*model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("units", unitRepository.findAll());
        model.addAttribute("courses", Courses.values());*/

        //ottenere il giornalista
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        /*if(credentials.getRole().equals("CHEF")){
            model.addAttribute("chef", credentials.getChef());
        }else if(credentials.getRole().equals("ADMINISTRATOR")){
            model.addAttribute("chefs", chefService.findAll());
        }*/
        model.addAttribute("journalist", credentials.getJournalist());
        return "journalist/createArticlePage.html";
    }

    @PostMapping("/journalist/articleData")
    public String createArticle(@Valid @ModelAttribute Article article, BindingResult bindingResultArticle,
                                @RequestParam("date") LocalDate date,
                                @RequestParam("images") MultipartFile[] files) {
        
        if(bindingResultArticle.hasErrors()){
          return "redirect:/journalist/createArticlePage.html?error=true";  
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
            article.setDate(date);
            this.articleService.save(article);
            
            return "redirect:/";
        }
    }
    
    

}
