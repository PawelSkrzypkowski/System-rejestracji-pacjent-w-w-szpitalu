package pl.edu.wat.web;

import lombok.Data;
import pl.edu.wat.model.enums.ProvinceEnum;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Data
public class RegisterView {
    private String fullname;
    private String pesel;
    private String email;
    private String login;
    private String password;
    private String phone;
    private ProvinceEnum province;
    private String city;
    private String street;
    private Integer houseNumber;
    private Integer flatNumber;
    private String doctorFullname;
}
