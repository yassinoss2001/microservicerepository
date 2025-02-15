package tn.esprit.microserviceproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.microserviceproject.entities.Menu;

public interface menuRepository extends JpaRepository<Menu, Long> {
}
