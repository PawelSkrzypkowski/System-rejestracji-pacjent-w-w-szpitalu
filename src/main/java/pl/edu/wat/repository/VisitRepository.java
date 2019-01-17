package pl.edu.wat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.model.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit,Long> {

    @Query("SELECT v FROM Visit v WHERE (v.busyVisit is null or v.busyVisit = true) AND (v.patientAlerted is null or v.patientAlerted = false) AND v.visitDate < ?1")
    List<Visit> getVisitsToAlertPatients(LocalDateTime maxDate);

    @Modifying
    @Transactional
    @Query("UPDATE Visit v SET v.patientAlerted=true WHERE v.id=?1")
    void setPatientAlertedToTrue(Long visitId);
}
