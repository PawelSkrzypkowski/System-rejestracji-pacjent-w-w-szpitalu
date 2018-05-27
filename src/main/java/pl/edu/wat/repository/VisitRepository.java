package pl.edu.wat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.model.Visit;

public interface VisitRepository extends JpaRepository<Visit,Long> {
}
