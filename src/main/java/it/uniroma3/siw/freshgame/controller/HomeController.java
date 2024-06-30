package it.uniroma3.siw.freshgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.freshgame.model.Genres;
import it.uniroma3.siw.freshgame.model.Tag;
import it.uniroma3.siw.freshgame.service.ArticleService;

@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/")
    public String getHomePage(Model model){
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tag.values());
        model.addAttribute("articles", this.articleService.getRandomArticles(5));
        return "homePage.html";
        //return "homePage2.html";
    }

}
