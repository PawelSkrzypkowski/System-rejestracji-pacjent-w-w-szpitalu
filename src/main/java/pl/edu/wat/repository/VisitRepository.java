package pl.edu.wat.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.wat.model.Visit;

public interface VisitRepository extends CrudRepository<Visit,Long> {
}
