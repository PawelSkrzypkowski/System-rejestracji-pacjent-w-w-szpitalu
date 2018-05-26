package pl.edu.wat.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@ToString
@Entity
@NoArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @NotEmpty
    private LocalDateTime visitDate;

    private boolean busyVisit=false;

    private int officeNumber;

    @Builder
    protected Visit(Long id, String description, LocalDateTime visitDate, boolean busyVisit, int officeNumber) {
        this.id = id;
        this.description = description;
        this.visitDate = visitDate;
        this.busyVisit = busyVisit;
        this.officeNumber = officeNumber;
    }
}
