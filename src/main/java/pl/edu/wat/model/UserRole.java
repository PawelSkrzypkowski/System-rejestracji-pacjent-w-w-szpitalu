package pl.edu.wat.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Data
@Entity
@ToString
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue
    private Long id;
    private String role;
    private String description;

    @Builder
    protected UserRole(Long id,String role, String description) {
        this.id = id;
        this.role = role;
        this.description = description;
    }
}
