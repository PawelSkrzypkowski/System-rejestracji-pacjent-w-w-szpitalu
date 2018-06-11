package pl.edu.wat.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Data
@ToString
@Entity
@NoArgsConstructor
public class PatientOnWard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Ward ward;

    private Date admissionDate;

    private Date dischargeDate;

    private Integer bedNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private User patient;

    @Builder
    public PatientOnWard(Ward ward, Date addmissionDate, Date dischargeDate, Integer bedNumber, User patient) {
        this.ward = ward;
        this.admissionDate = addmissionDate;
        this.dischargeDate = dischargeDate;
        this.bedNumber = bedNumber;
        this.patient = patient;
    }
}