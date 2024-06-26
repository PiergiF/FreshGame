package com.uniroma3.siw.freshgame.model;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

public class Game {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeGioco;
    private String descrizione;
    @Lob
    private String immagine;
    private String categoria;

    @OneToMany(mappedBy = "gioco")
    private List<Articolo> articoliGioco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeGioco() {
        return nomeGioco;
    }

    public void setNomeGioco(String nomeGioco) {
        this.nomeGioco = nomeGioco;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Articolo> getArticoliGioco() {
        return articoliGioco;
    }

    public void setArticoliGioco(List<Articolo> articoliGioco) {
        this.articoliGioco = articoliGioco;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nomeGioco == null) ? 0 : nomeGioco.hashCode());
        result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (nomeGioco == null) {
            if (other.nomeGioco != null)
                return false;
        } else if (!nomeGioco.equals(other.nomeGioco))
            return false;
        if (categoria == null) {
            if (other.categoria != null)
                return false;
        } else if (!categoria.equals(other.categoria))
            return false;
        return true;
    }

}
