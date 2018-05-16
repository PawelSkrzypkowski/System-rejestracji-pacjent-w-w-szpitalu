package pl.edu.wat.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import pl.edu.wat.model.enums.ProvinceEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Data
@ToString
@Entity
@Builder
public class Address{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotEmpty
        Integer houseNumber;

        Integer flatNumber;

        @NotEmpty
        String street;

        @NotEmpty
        String city;

        @NotEmpty
        ProvinceEnum provinceEnum;
}
