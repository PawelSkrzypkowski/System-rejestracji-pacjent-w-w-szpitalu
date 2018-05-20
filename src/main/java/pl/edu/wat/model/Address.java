package pl.edu.wat.model;

import lombok.*;
import pl.edu.wat.model.enums.ProvinceEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Data
@ToString
@Entity
@NoArgsConstructor
public class Address{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        Integer houseNumber;

        Integer flatNumber;

        @NotEmpty
        String street;

        @NotEmpty
        String city;

        @NotNull
        ProvinceEnum provinceEnum;

        @Builder
        protected Address(Long id, Integer houseNumber, Integer flatNumber, String street, String city, ProvinceEnum provinceEnum) {
                this.id = id;
                this.houseNumber = houseNumber;
                this.flatNumber = flatNumber;
                this.street = street;
                this.city = city;
                this.provinceEnum = provinceEnum;
        }
}
