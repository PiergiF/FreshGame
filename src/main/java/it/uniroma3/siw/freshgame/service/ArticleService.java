package it.uniroma3.siw.freshgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Game;
import it.uniroma3.siw.freshgame.model.Journalist;
import it.uniroma3.siw.freshgame.repository.ArticleRepository;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAllArticles(){
        return this.articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id){
        return this.articleRepository.findById(id);
    }

    public List<Article> getAllArticlesByJournalist(Journalist journalist){
        return this.articleRepository.findAllByJournalist(journalist);
    }

    public List<Article> getAllArticlesByGame(Game game){
        return this.articleRepository.findAllByGame(game);
    }

}
