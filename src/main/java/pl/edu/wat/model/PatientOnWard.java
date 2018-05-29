package pl.edu.wat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
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

    @ManyToOne
    private Ward ward;

    private Date admissionDate;

    private Date dischargeDate;

    private Integer bedNumber;

    @ManyToOne
    private User patient;
}