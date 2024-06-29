package it.uniroma3.siw.freshgame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import it.uniroma3.siw.freshgame.model.Credentials;
import jakarta.transaction.Transactional;

public interface CredentialsRepository extends JpaRepository<Credentials,Long> {

    public Optional<Credentials>findByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Credentials c WHERE c.journalist.id = :journalistId")
    public void deleteByJournalistId(Long journalistId);

}
