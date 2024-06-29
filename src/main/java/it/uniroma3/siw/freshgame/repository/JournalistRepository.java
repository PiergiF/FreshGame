package it.uniroma3.siw.freshgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.freshgame.model.Journalist;

public interface JournalistRepository extends JpaRepository<Journalist,Long> {

}
