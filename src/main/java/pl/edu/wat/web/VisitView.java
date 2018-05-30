package pl.edu.wat.web;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

@Data
public class VisitView {
    @NotEmpty
    private String visitDate;
    @NotEmpty
    private String visitTime;
    @NotNull
    private Integer officeNumber;
}
