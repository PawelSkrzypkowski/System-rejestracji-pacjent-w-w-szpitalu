package pl.edu.wat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wat.dto.VisitDTO;
import pl.edu.wat.exception.NotFoundException;
import pl.edu.wat.model.Address;
import pl.edu.wat.model.User;
import pl.edu.wat.model.UserRole;
import pl.edu.wat.model.Visit;
import pl.edu.wat.repository.AddressRepository;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.repository.UserRoleRepository;
import pl.edu.wat.repository.VisitRepository;
import pl.edu.wat.security.UserDetailsProvider;
import pl.edu.wat.web.DoctorRegisterView;
import pl.edu.wat.web.RegisterView;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Service
public class UserService{

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String DOCTOR_ROLE = "ROLE_DOCTOR";
    private static final String NURSE_ROLE = "ROLE_NURSE";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void addWithDefaultRole(RegisterView registerView) {
        User user = User.builder().fullname(registerView.getFullname()).pesel(registerView.getPesel()).
                login(registerView.getLogin()).password(passwordEncoder.encode(registerView.getPassword())).email(registerView.getEmail()).
                phone(registerView.getPhone()).doctorFullname(registerView.getDoctorFullname()).roles(new LinkedHashSet<>()).build();
        Address address = Address.builder().provinceEnum(registerView.getProvince()).city(registerView.getCity()).
                street(registerView.getStreet()).houseNumber(registerView.getHouseNumber()).
                flatNumber(registerView.getFlatNumber()).build();
        Address savedAddress = addressRepository.save(address);
        user.setAddress(savedAddress);
        UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        userRepository.save(user);
    }

    public void addWithDoctorRole(DoctorRegisterView registerView) {
        User user = User.builder().fullname(registerView.getFullname()).pesel(registerView.getPesel()).
                login(registerView.getLogin()).password(passwordEncoder.encode(registerView.getPassword())).email(registerView.getEmail()).
                phone(registerView.getPhone()).roles(new LinkedHashSet<>()).job(registerView.getJob()).specialization(registerView.getSpecialization()).build();
        UserRole doctorRole = roleRepository.findByRole(DOCTOR_ROLE);
        user.getRoles().add(doctorRole);
        userRepository.save(user);
    }

    public List<User> getAllStaff(){
//        String s1 = "2017-09-10 10:30:00";
//        String s2 = "2017-09-11 11:00:00";
//        String s3 = "2018-09-12 11:30:00";
//        String s4 = "2018-09-13 15:30:00";
//        String s5 = "2018-09-14 16:00:00";
//        String s6 = "2018-09-15 17:30:00";
//        String s7 = "2018-09-16 18:30:00";
//        String s8 = "2018-09-17 19:00:00";
//        String s9 = "2018-09-18 19:30:00";
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime start1 = LocalDateTime.parse(s1,dtf);
//        LocalDateTime start2 = LocalDateTime.parse(s2,dtf);
//        LocalDateTime start3 = LocalDateTime.parse(s3,dtf);
//        LocalDateTime start4 = LocalDateTime.parse(s4,dtf);
//        LocalDateTime start5 = LocalDateTime.parse(s5,dtf);
//        LocalDateTime start6 = LocalDateTime.parse(s6,dtf);
//        LocalDateTime start7 = LocalDateTime.parse(s7,dtf);
//        LocalDateTime start8 = LocalDateTime.parse(s8,dtf);
//        LocalDateTime start9 = LocalDateTime.parse(s9,dtf);
//        Visit visit1 = new Visit("brak mózgu",start1,false,1);
//        Visit visit2 = new Visit("hyhy",start2,false,1);
//        Visit visit3 = new Visit("hehe",start3,false,1);
//        Visit visit4 = new Visit("444",start4,false,1);
//        Visit visit5 = new Visit("5555",start5,false,1);
//        Visit visit6 = new Visit("666",start6,false,1);
//        Visit visit7 = new Visit("777",start7,false,1);
//        Visit visit8 = new Visit("888",start8,false,1);
//        Visit visit9 = new Visit("999",start9,false,1);
        List<User> staff = userRepository.findAll().stream()
                .filter(pr -> pr.getRoles().stream().anyMatch(s -> s.getId().intValue()==2))
                .collect(Collectors.toList());
        //userRepository.findAll().stream().filter(s -> s.getRoles().stream().filter(e -> e.getId()==2).forEach(staff:add)).forEach(staff::add);
//        staff.get(0).getVisits().add(visit1);
//        staff.get(0).getVisits().add(visit2);
//        staff.get(0).getVisits().add(visit3);
//        staff.get(0).getVisits().add(visit4);
//        staff.get(0).getVisits().add(visit5);
//        staff.get(0).getVisits().add(visit6);
//        staff.get(1).getVisits().add(visit7);
//        staff.get(1).getVisits().add(visit8);
//        staff.get(1).getVisits().add(visit9);
//        userRepository.save(staff.get(0));
//        userRepository.save(staff.get(1));
        return staff;
    }

    public List<Visit> getDoctorSchedule(Long id){
        List<Visit> visits = new ArrayList<>();
//        userRepository.findById(id).get().getVisits().stream().filter(s-> (s.getVisitDate().isAfter(LocalDateTime.now())==true)).forEach(visits::add);
        userRepository.findById(id).get().getVisits().stream().forEach(visits::add);
        return visits;
    }

    public User findUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public User findUser(String userName){
        return userRepository.findByLogin(userName);
    }

    public User getDoctorByVisit(Long id){
        return userRepository.findAll().stream()
                .filter(pr -> pr.getRoles().stream().anyMatch(s -> s.getId().intValue()==2))
                .filter(pr1 -> pr1.getVisits().stream().anyMatch(c -> c.getId()==id))
                .findFirst().get();
    }

    public List<VisitDTO> getHistoricalVisits(){
        List<VisitDTO> visits = new ArrayList<>();
        User user = userRepository.findByLogin(UserDetailsProvider.getCurrentUserUsername());

        for(Visit visit:user.getVisits())
            if(visit.getVisitDate().isBefore(LocalDateTime.now()))
                visits.add(VisitDTO.builder()
                        .userId(user.getId())
                        .doctorId(getDoctorByVisit(visit.getId()).getId())
                        .visitId(visit.getId())
                        .doctorName(getDoctorByVisit(visit.getId()).getFullname())
                        .userName(user.getFullname())
                        .visitDescription(visit.getDescription())
                        .visitDate(visit.getVisitDate())
                        .build());

        return visits;
    }

    public List<VisitDTO> getFutureVisits() {
        List<VisitDTO> visits = new ArrayList<>();
        User user = userRepository.findByLogin(UserDetailsProvider.getCurrentUserUsername());

        for (Visit visit : user.getVisits())
            if (visit.getVisitDate().isAfter(LocalDateTime.now()) && visit.isBusyVisit())
                visits.add(VisitDTO.builder()
                        .userId(user.getId())
                        .doctorId(getDoctorByVisit(visit.getId()).getId())
                        .visitId(visit.getId())
                        .doctorName(getDoctorByVisit(visit.getId()).getFullname())
                        .userName(user.getFullname())
                        .visitDescription(visit.getDescription())
                        .visitDate(visit.getVisitDate())
                        .build());

        return visits;
    }

    public void addWithNurseRole(DoctorRegisterView registerView) {
        User user = User.builder().fullname(registerView.getFullname()).pesel(registerView.getPesel()).
                login(registerView.getLogin()).password(passwordEncoder.encode(registerView.getPassword())).email(registerView.getEmail()).
                phone(registerView.getPhone()).roles(new LinkedHashSet<>()).job(registerView.getJob()).specialization(registerView.getSpecialization()).build();
        UserRole doctorRole = roleRepository.findByRole(NURSE_ROLE);
        user.getRoles().add(doctorRole);
        userRepository.save(user);
    }
}
