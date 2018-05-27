package pl.edu.wat.web;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PeselView {
    @NotEmpty
    String pesel;
}
