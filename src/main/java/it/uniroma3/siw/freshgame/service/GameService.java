package it.uniroma3.siw.freshgame.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Article;
import it.uniroma3.siw.freshgame.model.Game;
import it.uniroma3.siw.freshgame.model.Genres;
import it.uniroma3.siw.freshgame.model.Platforms;
import it.uniroma3.siw.freshgame.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> getAllGames(){
        return this.gameRepository.findAll();
    }

    public Game getGameById(Long id){
        return this.gameRepository.findById(id).orElse(null);
    }

    public Game save(Game game){
        return this.gameRepository.save(game);
    }

    public List<Game> getAllGamesOrderedByDate(){
        List<Game> allGames = this.getAllGames();
        if(allGames != null && allGames.size() >1){
            Collections.sort(allGames, new Comparator<Game>() {
                public int compare(Game g1, Game g2) {
                    return g2.getReleaseDate().compareTo(g1.getReleaseDate());
                }
            });
        }
        return allGames;
    }

    public List<Game> getAllGamesByPlatform(Platforms platform){
        List<Game> all = this.getAllGames();
        List<Platforms> gamePlatforms = new ArrayList<>();
        List<Game> result = new ArrayList<>();
        for(Game game : all){
            gamePlatforms = game.getPlatforms();
            if(gamePlatforms.contains(platform)){
                result.add(game);
            }
        }
        return result;
    }

    public List<Game> getAllGamesByGenre(Genres genre){
        List<Game> all = this.getAllGames();
        List<Genres> gameGenres = new ArrayList<>();
        List<Game> result = new ArrayList<>();
        for(Game game : all){
            gameGenres = game.getGenres();
            if(gameGenres.contains(genre)){
                result.add(game);
            }
        }
        return result;
    }

}