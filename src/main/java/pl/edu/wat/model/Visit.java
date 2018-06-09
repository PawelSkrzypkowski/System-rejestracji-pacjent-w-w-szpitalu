package pl.edu.wat.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@ToString
@Entity
@NoArgsConstructor
public class Visit implements Comparable<Visit>{
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

    @Override
    public int compareTo(Visit o) {
        return getVisitDate().compareTo(o.getVisitDate());
    }


}
