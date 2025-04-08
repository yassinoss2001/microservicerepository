package tn.esprit.microserviceproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microserviceproject.entities.Avis;
import tn.esprit.microserviceproject.repositories.AvisRepository;
import tn.esprit.microserviceproject.services.AvisService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avis")
public class AvisRestController {
    @Autowired
    private AvisService avisService;

    @Autowired
    private AvisRepository avisRepository;

    @GetMapping
    public List<Avis> getAllAvis() {
        return avisService.getAllAvis();
    }

    @GetMapping("/{id}")
    public Optional<Avis> getAvisById(@PathVariable int id) {
        return avisService.getAvisById(id);
    }

    @PostMapping
    public Avis addAvis(@RequestBody Avis avis) {
        return avisService.addAvis(avis);
    }

    @PutMapping("/{id}")
    public Avis updateAvis(@PathVariable int id, @RequestBody Avis avisDetails) {
        return avisService.updateAvis(id, avisDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteAvis(@PathVariable int id) {
        avisService.deleteAvis(id);
    }

    @GetMapping("/average")
    public double getAverageRating() {
        Double moyenne = avisRepository.getAverageRating();
        return moyenne != null ? moyenne : 0.0;
    }
}
