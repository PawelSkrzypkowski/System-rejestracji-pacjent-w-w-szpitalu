package pl.edu.wat.web;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DiseaseView {
    @NotEmpty
    private String diseaseName;
}
