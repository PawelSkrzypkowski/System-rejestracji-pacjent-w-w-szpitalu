package pl.edu.wat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.wat.model.Ward;

public interface WardRepository extends JpaRepository<Ward, Long> {
    Ward findByWardNameAndRoomNumber(String wardName, Integer roomNumber);
}
