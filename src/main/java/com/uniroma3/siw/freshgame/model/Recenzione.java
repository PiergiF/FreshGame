package com.uniroma3.siw.freshgame.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Recenzione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float recenzione ;
    private String commento;
    private Utente utente;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public float getRecenzione() {
        return recenzione;
    }
    public void setRecenzione(float recenzione) {
        this.recenzione = recenzione;
    }
    public String getCommento() {
        return commento;
    }
    public void setCommento(String commento) {
        this.commento = commento;
    }
    public Utente getUtente() {
        return utente;
    }
    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((utente == null) ? 0 : utente.hashCode());
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
        Recenzione other = (Recenzione) obj;
        if (utente == null) {
            if (other.utente != null)
                return false;
        } else if (!utente.equals(other.utente))
            return false;
        return true;
    }

    
}
