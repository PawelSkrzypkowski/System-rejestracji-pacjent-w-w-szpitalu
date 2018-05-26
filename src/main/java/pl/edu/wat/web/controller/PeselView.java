package pl.edu.wat.web.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PeselView {
    @NotEmpty
    String pesel;
}
