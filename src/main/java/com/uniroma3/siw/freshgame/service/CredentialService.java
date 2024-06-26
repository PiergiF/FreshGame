package com.uniroma3.siw.freshgame.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.uniroma3.siw.freshgame.model.Credential;
import com.uniroma3.siw.freshgame.repository.CredentialRepository;
import java.util.List;
import java.util.Optional;

public class CredentialService {

        @Autowired
    private CredentialRepository credentialRepository;

    public Credential save(Credential credential) {
        return credentialRepository.save(credential);
    }

    public List<Credential> findAll() {
        return credentialRepository.findAll();
    }

    public Optional<Credential> findById(Long id) {
        return credentialRepository.findById(id);
    }

    public Credential findByUsername(String username) {
        return credentialRepository.findByUsername(username);
    }

    public void deleteById(Long id) {
        credentialRepository.deleteById(id);
    }
}
