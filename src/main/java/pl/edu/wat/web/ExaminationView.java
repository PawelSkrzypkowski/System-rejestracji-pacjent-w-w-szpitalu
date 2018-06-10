package pl.edu.wat.web;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ExaminationView {
    @NotEmpty
    private String name;
}
