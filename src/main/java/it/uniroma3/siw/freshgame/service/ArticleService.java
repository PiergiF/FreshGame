package it.uniroma3.siw.freshgame.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public Article save(Article article){
        return this.articleRepository.save(article);
    }

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

    public List<Article> getRandomArticles(int numElements){
        List<Article> all = this.getAllArticles();
        List<Article> randomArticles = new ArrayList<>();
        int randomIndex;
        Set<Integer> s = new HashSet<>();
        if(all.size() > numElements){
            for(int i=0; i<numElements; i++){
                while (true) {
                    randomIndex = (int) (Math.random() * all.size());
                    if(!s.contains(randomIndex)){
                        s.add(randomIndex);
                        randomArticles.add(all.get(randomIndex));
                        break;
                    }
                }
            }
            return randomArticles;
        }else{
            return all;
        }
    }

}
