package pl.edu.wat.web;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Data
public class DoctorRegisterView {
    @NotEmpty
    private String fullname;
    @NotEmpty
    private String pesel;

    private String email;
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String job;
    @NotEmpty
    private String specialization;
}
