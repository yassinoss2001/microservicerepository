package tn.esprit.microserviceproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.microserviceproject.entities.Avis;

public interface AvisRepository extends JpaRepository<Avis,Integer> {
    @Query("SELECT AVG(a.note) FROM Avis a")
    Double getAverageRating();
}
