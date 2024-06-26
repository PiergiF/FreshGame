package com.uniroma3.siw.freshgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniroma3.siw.freshgame.model.Credential;


public interface CredentialRepository extends JpaRepository<Credential, Long> {

    public Credential findByUsername(String username);

}
