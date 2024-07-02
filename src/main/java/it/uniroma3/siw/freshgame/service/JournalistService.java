package it.uniroma3.siw.freshgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Journalist;
import it.uniroma3.siw.freshgame.repository.JournalistRepository;

@Service
public class JournalistService {

    @Autowired
    private JournalistRepository journalistRepository;

    public List<Journalist> getAllJournalists(){
        return this.journalistRepository.findAll();
    }

    public Journalist getJournalistById(Long id){
        return this.journalistRepository.findById(id).orElse(null);
    }

    public Journalist save(Journalist journalist){
        return this.journalistRepository.save(journalist);
    }

    public void deleteById(Long id){
        journalistRepository.deleteById(id);
    }
}
