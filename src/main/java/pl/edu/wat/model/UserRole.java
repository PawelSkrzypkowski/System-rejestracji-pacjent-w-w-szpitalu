package pl.edu.wat.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Data
@Entity
public class UserRole {

    @Id
    @GeneratedValue
    private Long id;
    private String role;
    private String description;
}
