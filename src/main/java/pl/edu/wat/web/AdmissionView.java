package pl.edu.wat.web;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AdmissionView {
    @NotEmpty
    private String plannedReleaseDate;
    @NotEmpty
    private String plannedReleaseTime;
    @NotNull
    private Integer roomNumber;
    @NotNull
    private Integer bedNumber;
    @NotEmpty
    private String wardName;
}
