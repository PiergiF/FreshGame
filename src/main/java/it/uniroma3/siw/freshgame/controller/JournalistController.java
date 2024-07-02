package it.uniroma3.siw.freshgame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Genres;
import it.uniroma3.siw.freshgame.model.Journalist;
import it.uniroma3.siw.freshgame.model.Platforms;
import it.uniroma3.siw.freshgame.model.Tags;
import it.uniroma3.siw.freshgame.service.ArticleService;
import it.uniroma3.siw.freshgame.service.JournalistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class JournalistController {

    @Autowired
    private JournalistService journalistService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/all/journalistPage/{id}")
    public String getJournalistPage(@PathVariable("id")Long journalistId, Model model) {
        //per la sub navbar
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("platforms", Platforms.values());

        //il giornalista
        Journalist journalist = this.journalistService.getJournalistById(journalistId);
        model.addAttribute("journalist", journalist);

        //articoli recensioni
        List<Article> reviewArticleJournalist = this.articleService.getAllReviewArticlesByJournalist(journalist);
        List<Article> orderedReviewArticleJournalist = this.articleService.orderSpecificArticleListByDateTime(reviewArticleJournalist);
        model.addAttribute("reviewsArticles", orderedReviewArticleJournalist);

        //tutti gli articoli
        List<Article> allJournalistArticles = this.articleService.getAllArticlesByJournalist(journalist);
        List<Article> allOrderedJournalistArticles = this.articleService.orderSpecificArticleListByDateTime(allJournalistArticles);
        model.addAttribute("allArticles", allOrderedJournalistArticles);

        //se abilitare i permessi perché è il giornalista che visita la sua pagina
        Long loggedId = (Long) model.getAttribute("loggedId");
        if(journalistId == loggedId){
            model.addAttribute("isSelf", true);
        }

        return "all/journalistPage.html";
    }

    @GetMapping("/journalist/personalPage")
    public String getPersonalPage(Model model) {
        return "redirect:/all/journalistPage/" + model.getAttribute("loggedId");
    }

    @GetMapping("/all/allJournalistPage")
    public String getAllJournalistPage(Model model) {
        model.addAttribute("journalists", this.journalistService.getAllJournalists());
        return "all/allJournalistPage.html";
    }
    
    
    
}
