package com.mexpedition.web.controller;

import com.mexpedition.dao.ExpeditionDao;
import com.mexpedition.model.Expedition;
import com.mexpedition.web.exception.ExpeditionNotFoundException;
import com.mexpedition.web.exception.ImpossibleAjouterExpeditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ExpeditionController {

    @Autowired
    ExpeditionDao expeditionDao;

    /**
     *
     * method which create a new expedition in base
     * @param expedition
     * @return a ResponseEntity if ok or throw an exception
     */
    @PostMapping (value="/expedition")
    public ResponseEntity<Expedition> ajouterExpedition (@RequestBody Expedition expedition) {

        Expedition nouvelleExpedition = expeditionDao.save(expedition);
        if(nouvelleExpedition == null) throw new ImpossibleAjouterExpeditionException("Impossible d'ajouter cette commande");

        return new ResponseEntity<Expedition>(expedition, HttpStatus.CREATED);
    }

    /**
     * method who get an expedition by her unique id
     * @param id
     * @return the expedition if found or throw an exception
     */
    @GetMapping(value="/expedition/{id}")
    public Optional<Expedition> recupererExpedition(@PathVariable int id){
        Optional<Expedition> expedition = expeditionDao.findById(id);

        if(!expedition.isPresent()) throw new ExpeditionNotFoundException("Cette commande n'existe pas");

        return expedition;
    }

    /**
     * method which update an expedition with new value(s)
     * No return
     * @param expedition
     */
    @PutMapping (value="/expedition")
    public void updateExpedition(@RequestBody Expedition expedition){

        expeditionDao.save(expedition);
    }
}
