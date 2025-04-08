package tn.esprit.microserviceproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microserviceproject.entities.Avis;
import tn.esprit.microserviceproject.repositories.AvisRepository;


import java.util.List;
import java.util.Optional;

@Service
public class AvisService {
    @Autowired
    private AvisRepository avisRepository;

    public List<Avis> getAllAvis() {
        return avisRepository.findAll();
    }

    public Optional<Avis> getAvisById(int id) {
        return avisRepository.findById(id);
    }

    public Avis addAvis(Avis avis) {
        return avisRepository.save(avis);
    }

    public Avis updateAvis(int id, Avis avisDetails) {
        return avisRepository.findById(id).map(avis -> {
            avis.setClientNom(avisDetails.getClientNom());
            avis.setNote(avisDetails.getNote());
            avis.setCommentaire(avisDetails.getCommentaire());
            avis.setResolu(avisDetails.isResolu());
            return avisRepository.save(avis);
        }).orElseThrow(() -> new RuntimeException("Avis non trouvé"));
    }

    public void deleteAvis(int id) {
        avisRepository.deleteById(id);
    }
}
