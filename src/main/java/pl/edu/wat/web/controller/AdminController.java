package pl.edu.wat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.wat.model.User;
import pl.edu.wat.model.enums.JobEnum;
import pl.edu.wat.model.enums.ProvinceEnum;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.service.UserService;
import pl.edu.wat.web.DoctorRegisterView;
import pl.edu.wat.web.RegisterView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registerPersonnel")
    public String register(Model model) {
        Map<JobEnum, String> jobMap = new HashMap<>();
        Arrays.stream(JobEnum.values()).forEach(e -> {
            jobMap.put(e, JobEnum.getValue(e));
        });
        model.addAttribute("jobMap", jobMap);
        return "register/registerDoctor";
    }

    @PostMapping("/registerPersonnel")
    public String addUser(@ModelAttribute @Valid DoctorRegisterView registerView,
                          BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return "register/registerDoctorValidationError";
        } else {
            User user = userRepository.findByLogin(registerView.getLogin());
            if (user != null) {
                return "register/registerDoctorError";
            }
            if(registerView.getJob().equals(JobEnum.DOCTOR))
                userService.addWithDoctorRole(registerView);
            else
                userService.addWithNurseRole(registerView);
            return "register/registerDoctorSuccess";
        }
    }
}
