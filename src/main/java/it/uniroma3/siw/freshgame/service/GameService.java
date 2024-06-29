package it.uniroma3.siw.freshgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Game;
import it.uniroma3.siw.freshgame.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> getAllGames(){
        return this.gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long id){
        return this.gameRepository.findById(id);
    }

}
