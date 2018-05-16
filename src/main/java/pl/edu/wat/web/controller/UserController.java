package pl.edu.wat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.wat.model.User;
import pl.edu.wat.model.enums.ProvinceEnum;
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

    @GetMapping("/register")
    public String register(Model model) {
        Map<ProvinceEnum, String> provinceMap = new HashMap<>();
        Arrays.stream(ProvinceEnum.values()).forEach(e -> {
            provinceMap.put(e, ProvinceEnum.getValue(e));
        });
        model.addAttribute("provinceMap", provinceMap);
        return "registerForm";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute @Valid RegisterView registerView,
                          BindingResult bindResult) {
        if(bindResult.hasErrors())
            return "registerForm";
        else {
            userService.addWithDefaultRole(registerView);
            return "registerSuccess";
        }
    }

}
