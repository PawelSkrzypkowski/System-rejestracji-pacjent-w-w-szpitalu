package pl.edu.wat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.model.PatientOnWard;

@Repository
public interface PatientOnWardRepository extends JpaRepository<PatientOnWard, Long> {
    PatientOnWard findByPatientId(Long patientId);
}
