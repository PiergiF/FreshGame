package it.uniroma3.siw.freshgame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Game;
import it.uniroma3.siw.freshgame.model.Journalist;

public interface ArticleRepository extends JpaRepository<Article,Long>{

    public List<Article> findAllByJournalist(Journalist journalis);
    public List<Article> findAllByGame(Game game);

}
