package it.uniroma3.siw.freshgame.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Reader {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String surname;

    private String gamertag;

    @OneToMany(mappedBy = "reader")
    private List<Review> reviews;

    public Reader() {
    }

    public Reader(String name, String surname, String gamertag) {
        this.name = name;
        this.surname = surname;
        this.gamertag = gamertag;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGamertag() {
        return gamertag;
    }

    public void setGamertag(String gamertag) {
        this.gamertag = gamertag;
    }
}
