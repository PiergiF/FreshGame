package it.uniroma3.siw.freshgame.controller.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Game;
import it.uniroma3.siw.freshgame.model.Tags;
import it.uniroma3.siw.freshgame.repository.ArticleRepository;
import it.uniroma3.siw.freshgame.service.ArticleService;

@Component
public class ArticleValidator implements Validator{

    @Autowired
    private ArticleService articleService;

    @Override
	public void validate(Object o, Errors errors) {
		Article article = (Article)o;
        boolean isReview = false;
        //List<Tags> t = article.getTags();
        for(Tags t : article.getTags()){
            if(t == Tags.REVIEWS){
                isReview = true;
            }
        }
        if(isReview && this.articleService.getReviewArticleOfSpecificGame(article.getGame()) != null){
            errors.reject("reviewArticle.duplicate");
        }
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Game.class.equals(aClass);
	}
}
