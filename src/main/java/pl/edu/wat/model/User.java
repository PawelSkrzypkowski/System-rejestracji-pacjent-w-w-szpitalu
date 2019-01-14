package pl.edu.wat.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wat.model.enums.JobEnum;
import pl.edu.wat.model.enums.ProvinceEnum;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Data
@ToString
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String fullname;

    private String email;

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @NotEmpty
    private String pesel;

    @NotEmpty
    private String phone;

    private JobEnum job;

    private String specialization;

    private String doctorFullname;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();

    @OneToOne
    private Address address;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Visit> visits = new ArrayList<>();

    @ManyToMany
    private List<Disease> diseases = new ArrayList<>();

    @ManyToMany
    private List<Examination> examinations = new ArrayList<>();

    @ManyToOne
    private Ward personnelWard;

    @Builder
    protected User(String fullname, String email, String login, String password, String pesel, String phone, JobEnum job, String specialization, String doctorFullname, Set<UserRole> roles, Address address, List<Visit> visits) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.pesel = pesel;
        this.phone = phone;
        this.job = job;
        this.specialization = specialization;
        this.doctorFullname = doctorFullname;
        this.roles = roles;
        this.address = address;
        this.visits = visits;
    }

    public String getProvince() {
        return ProvinceEnum.getValue(address.getProvinceEnum());
    }

    public void addDisease(Disease disease) {
        diseases.add(disease);
    }
}
