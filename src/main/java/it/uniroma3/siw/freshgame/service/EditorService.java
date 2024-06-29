package it.uniroma3.siw.freshgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.freshgame.model.Editor;
import it.uniroma3.siw.freshgame.repository.EditorRepository;

@Service
public class EditorService {

    @Autowired
    private EditorRepository editorRepository;

    public List<Editor> getAllEditors(){
        return this.editorRepository.findAll();
    }

    public Optional<Editor> getEditorById(Long id){
        return this.editorRepository.findById(id);
    }

}
