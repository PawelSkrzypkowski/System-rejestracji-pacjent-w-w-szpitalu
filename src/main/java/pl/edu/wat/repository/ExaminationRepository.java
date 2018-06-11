package pl.edu.wat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.model.Examination;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {
    public Examination findByName(String name);
}
