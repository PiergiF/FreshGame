package it.uniroma3.siw.freshgame.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.tomcat.util.openssl.pem_password_cb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Game;
import it.uniroma3.siw.freshgame.model.Journalist;
import it.uniroma3.siw.freshgame.model.Tags;
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

    public Article getArticleById(Long id){
        return this.articleRepository.findById(id).orElse(null);
    }

    public List<Article> getAllArticlesByJournalist(Journalist journalist){
        return this.articleRepository.findAllByJournalist(journalist);
    }

    public List<Article> getAllArticlesByGame(Game game){
        return this.articleRepository.findAllByGame(game);
    }

    public void deleteById(Long id){
        articleRepository.deleteById(id);
    }

    public List<Article> getAllArticlesByTag(Tags tag){
        List<Article> all = this.getAllArticles();
        List<Tags> articleTags = new ArrayList<>();;
        List<Article> result = new ArrayList<>();
        for(Article article : all){
            articleTags = article.getTags();
            if(articleTags.contains(tag)){
                result.add(article);
            }
        }
        return result;
        /*System.out.println(tags);
        List<Article> all = new ArrayList<>();
        for (Tags tag : tags) {
            all = this.articleRepository.findAllByTags(tag);
        }*/
        //return this.articleRepository.findAllByTags(tags);
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

    /*
    public List<Article> getAllArticlesOrderedByDate(){
        List<Article> orderedArticles = this.getAllArticles();
        if(orderedArticles != null && orderedArticles.size() >1){
            Collections.sort(orderedArticles, new Comparator<Article>() {
                public int compare(Article a1, Article a2) {
                    return a2.getDate().compareTo(a1.getDate());
                }
            });
        }
        return orderedArticles;
    }
    */

    /*
    public List<Article> getNArticlesOrderedByDate(int numElements){
        List<Article> all = this.getAllArticles();
        if(all != null && all.size() >1){
            Collections.sort(all, new Comparator<Article>() {
                public int compare(Article a1, Article a2) {
                    return a2.getDate().compareTo(a1.getDate());
                }
            });
            List<Article> orderedArticles = new ArrayList<>();
            if(all.size()>numElements){
                for (int i=0; i<numElements;i++) {
                    orderedArticles.add(all.get(i));
                }
            }else{
                for (int i=0; i<all.size();i++) {
                    orderedArticles.add(all.get(i));
                }
            }
            return orderedArticles;
        }else{
            return all;
        }
    }
    */

    public List<Article> getAllArticlesOrderedByDateTime(){
        List<Article> allArticles = this.getAllArticles();
        if(allArticles != null && allArticles.size() >1){
            Collections.sort(allArticles, new Comparator<Article>() {
                public int compare(Article a1, Article a2) {
                    return a2.getDateTime().compareTo(a1.getDateTime());
                }
            });
        }
        return allArticles;
    }


    public List<Article> getNArticlesOrderedByDateTime(int numElements){
        List<Article> all = this.getAllArticles();
        if(all != null && all.size() >1){
            Collections.sort(all, new Comparator<Article>() {
                public int compare(Article a1, Article a2) {
                    return a2.getDateTime().compareTo(a1.getDateTime());
                }
            });
            List<Article> orderedArticles = new ArrayList<>();
            if(all.size()>numElements){
                for (int i=0; i<numElements;i++) {
                    orderedArticles.add(all.get(i));
                }
            }else{
                for (int i=0; i<all.size();i++) {
                    orderedArticles.add(all.get(i));
                }
            }
            return orderedArticles;
        }else{
            return all;
        }
    }

    public List<Article> orderSpecificArticleListByDateTime(List<Article> listArticle){
        if(listArticle != null && listArticle.size() >1){
            Collections.sort(listArticle, new Comparator<Article>() {
                public int compare(Article a1, Article a2) {
                    return a2.getDateTime().compareTo(a1.getDateTime());
                }
            });
            /*List<Article> orderedArticles = new ArrayList<>();
            if(all.size()>numElements){
                for (int i=0; i<numElements;i++) {
                    orderedArticles.add(all.get(i));
                }
            }else{
                for (int i=0; i<listArticle.size();i++) {
                    orderedArticles.add(listArticle.get(i));
                }
            }
            return orderedArticles;*/
        }/*else{
            return all;
        }*/
        return listArticle;
    }

    public Article getReviewArticleOfSpecificGame(Game game){
        List<Article> onlyReviewsArticle = this.getAllArticlesByTag(Tags.REVIEWS);
        Article resultReviewArticle = null;
        for (Article article : onlyReviewsArticle) {
            if(article.getGame().equals(game)){
                resultReviewArticle = article;
            }
        }
        return resultReviewArticle;
    }

    public List<Article> getAllReviewArticlesByJournalist(Journalist journalist){
        List<Article> allJournalistArticles = this.getAllArticlesByJournalist(journalist);
        List<Article> resultReviewsArticles = new ArrayList<>();
        for(Article a : allJournalistArticles){
            if(a.getTags().contains(Tags.REVIEWS)){
                resultReviewsArticles.add(a);
            }
        }
        return resultReviewsArticles;
    }

    public List<Article> searchArticles(String query) {
        return articleRepository.findByTitleContainingIgnoreCase(query);
    }

    public void deleteArticlesByJournalist(Journalist journalist){
        List<Article> articles = this.getAllArticlesByJournalist(journalist);
        for(Article article : articles){
            //this.deleteRecipeIngredientsByRecipeId(recipe.getId());
            this.deleteById(article.getId());
        }
    }

}
