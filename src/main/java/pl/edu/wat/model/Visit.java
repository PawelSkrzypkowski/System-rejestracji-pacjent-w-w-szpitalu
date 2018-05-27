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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    private LocalDateTime visitDate;

    private boolean busyVisit=false;

    private int officeNumber;


    @Builder
    public Visit(String description, LocalDateTime visitDate, boolean busyVisit, int officeNumber) {
        this.description = description;
        this.visitDate = visitDate;
        this.busyVisit = busyVisit;
        this.officeNumber = officeNumber;
    }
}
