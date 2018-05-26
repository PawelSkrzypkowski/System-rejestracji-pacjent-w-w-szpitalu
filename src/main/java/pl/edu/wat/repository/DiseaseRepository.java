package pl.edu.wat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.model.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    Disease findByName(String name);
}
