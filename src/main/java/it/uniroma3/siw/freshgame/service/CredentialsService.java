package it.uniroma3.siw.freshgame.service;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Credentials;
import it.uniroma3.siw.freshgame.repository.CredentialsRepository;
import jakarta.transaction.Transactional;

@Service
public class CredentialsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredentialsRepository credentialsRepository;

    public Credentials saveCredentialsNoPassword(Credentials credentials){
        return this.credentialsRepository.save(credentials);
    }

    @Transactional
    public Credentials getCredentials(Long id) {
        Optional<Credentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Credentials getCredentials(String username) {
        Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }

    @Transactional
    public Credentials saveCredentials(Credentials credentials, String role) {
        if(role.equals("READER")){
            credentials.setRole(Credentials.READER_ROLE);
        }
        else if(role.equals("JOURNALIST")){
            credentials.setRole(Credentials.JOURNALIST_ROLE);
        }else if(role.equals("EDITOR")){
            credentials.setRole(Credentials.EDITOR_ROLE);
        }
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

    public void deleteByJournalistId(Long chefId){
        this.credentialsRepository.deleteByJournalistId(chefId);
    }

}
