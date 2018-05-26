package pl.edu.wat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.model.User;
import pl.edu.wat.model.enums.ProvinceEnum;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.service.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String register(Model model) {
        Map<ProvinceEnum, String> provinceMap = new HashMap<>();
        Arrays.stream(ProvinceEnum.values()).forEach(e -> {
            provinceMap.put(e, ProvinceEnum.getValue(e));
        });
        model.addAttribute("provinceMap", provinceMap);
        return "register/registerForm";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute @Valid RegisterView registerView,
                          BindingResult bindResult) {
        if(bindResult.hasErrors()) {
            return "register/validationError";
        } else {
            User user = userRepository.findByLogin(registerView.getLogin());
            if(user != null){
                return "register/registerError";
            }
            userService.addWithDefaultRole(registerView);
            return "register/registerSuccess";
        }
    }

    @RequestMapping("/staff")
    public String getStaff(Model model){
        model.addAttribute("doctors",userService.getAllStaff());
        return "staff";
    }

    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/details")
    public String getDoctorSchedule(@RequestParam long id, Model model){
        model.addAttribute("doctor", userService.findUser(id));
        model.addAttribute("visits", userService.getDoctorSchedule(id));
        return "visit/doctorSchedule";
    }
}
