package it.uniroma3.siw.freshgame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Reader;
import it.uniroma3.siw.freshgame.repository.ReaderRepository;
import jakarta.transaction.Transactional;

@Service
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    /**
     * This method retrieves all Readers from the DB.
     * @return a List with all the retrieved Users
     */
    public List<Reader> getAllReaders(){
        return (List<Reader>) this.readerRepository.findAll();
    }

    /**
     * This method retrieves a Reader from the DB based on its ID.
     * @param id the id of the Reader to retrieve from the DB
     * @return the retrieved Reader, or null if no Reader with the passed ID could be found in the DB
     */
    public Reader getReaderById(Long id){
        Optional<Reader> reader = this.readerRepository.findById(id);
        return reader.orElse(null);
    }

    /**
     * This method saves a Reader in the DB.
     * @param reader the reader to save into the DB
     * @return the saved Reader
     * @throws DataIntegrityViolationException if a Reader with the same username
     *                              as the passed Reader already exists in the DB
     */
    public Reader saveReader(Reader reader) {
        return this.readerRepository.save(reader);
    }

    /* 
    public void saveRecipeForCustomer(Customer customer, Recipe recipe) {
        customer.getSavedRecipes().add(recipe);
        customerRepository.save(customer);
    }

    public List<Recipe> getSavedRecipes(Customer customer) {
        return customer.getSavedRecipes();
    }

    public void removeRecipeForCustomer(Customer customer, Recipe recipe) {
        customer.getSavedRecipes().remove(recipe);
        customerRepository.save(customer);
    }
    */

}
