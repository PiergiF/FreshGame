package it.uniroma3.siw.freshgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.freshgame.model.Reader;
import it.uniroma3.siw.freshgame.repository.ReaderRepository;
import jakarta.persistence.Entity;

@Entity
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    public List<Reader> getAllReaders(){
        return this.readerRepository.findAll();
    }

    public Optional<Reader> getReaderById(Long id){
        return this.readerRepository.findById(id);
    }

}
