package com.esprit.gestionrecette.service;

import com.esprit.gestionrecette.entites.Recette;
import com.esprit.gestionrecette.repository.Recetterepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetteService {

    @Autowired
    private Recetterepository recetteRepository;


    public Recette createRecette(Recette recette) {
        return recetteRepository.save(recette);
    }


    public List<Recette> getAllRecettes() {
        return recetteRepository.findAll();
    }


    public Optional<Recette> getRecetteById(Long id) {
        return recetteRepository.findById(id);
    }


    public Recette updateRecette(Long id, Recette recetteDetails) {
        Recette recette = recetteRepository.findById(id).orElseThrow(() -> new RuntimeException("Recette non trouvée"));
        recette.setTitre(recetteDetails.getTitre());
        recette.setDescription(recetteDetails.getDescription());
        recette.setIngredients(recetteDetails.getIngredients());
        recette.setEtape(recetteDetails.getEtape());
        recette.setImage(recetteDetails.getImage());
        return recetteRepository.save(recette);
    }


    public void deleteRecette(Long id) {
        recetteRepository.deleteById(id);
    }

    public List<Recette> searchRecetteByTitreLike(String keyword) {
        return recetteRepository.findByTitreContainingIgnoreCase(keyword);
    }
}