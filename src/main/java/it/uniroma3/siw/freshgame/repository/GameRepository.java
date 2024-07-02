package it.uniroma3.siw.freshgame.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.freshgame.model.Game;

public interface GameRepository extends JpaRepository<Game,Long> {

    public boolean existsByNameAndReleaseDateAndSoftwareHouse(String name, LocalDate releaseDate, String softwareHouse);
}
