package it.uniroma3.siw.freshgame.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.freshgame.model.Game;
import it.uniroma3.siw.freshgame.repository.GameRepository;
import it.uniroma3.siw.freshgame.service.GameService;

@Component
public class GameValidator implements Validator {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Override
	public void validate(Object o, Errors errors) {
		Game game = (Game)o;
		if (game.getName()!=null && game.getReleaseDate()!=null 
				&& game.getSoftwareHouse()!=null && gameRepository.existsByNameAndReleaseDateAndSoftwareHouse(game.getName(), game.getReleaseDate(), game.getSoftwareHouse())) {
			//errors.reject("game.duplicate");
			errors.reject("game.duplicate", "Non è possibile inserire più volte lo stesso oggetto");
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Game.class.equals(aClass);
	}
}
