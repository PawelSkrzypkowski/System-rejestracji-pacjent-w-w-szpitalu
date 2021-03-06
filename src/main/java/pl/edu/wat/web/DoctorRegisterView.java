package pl.edu.wat.web;

import lombok.Data;
import pl.edu.wat.model.enums.JobEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private JobEnum job;
    @NotEmpty
    private String specialization;
}
