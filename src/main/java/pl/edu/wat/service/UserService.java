package pl.edu.wat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wat.exception.NotFoundException;
import pl.edu.wat.model.Address;
import pl.edu.wat.model.User;
import pl.edu.wat.model.UserRole;
import pl.edu.wat.model.Visit;
import pl.edu.wat.repository.AddressRepository;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.repository.UserRoleRepository;
import pl.edu.wat.web.DoctorRegisterView;
import pl.edu.wat.web.RegisterView;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Service
public class UserService{

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String DOCTOR_ROLE = "ROLE_DOCTOR";

    @Autowired
    private UserRepository userRepository;

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
        List<User> staff = new ArrayList<>();
        userRepository.findAll().stream().filter(s -> s.getSpecialization()!=null).forEach(staff::add);
        return staff;
    }

    public List<Visit> getDoctorSchedule(Long id){
        List<Visit> visits = userRepository.findById(id).get().getVisits();
        return visits;
    }

    public User findUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }
}
