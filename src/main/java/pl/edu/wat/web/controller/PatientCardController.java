package pl.edu.wat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.model.User;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.web.PeselView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/card")
public class PatientCardController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String findPatient() {

        return "patientCard/patientCardFind";
    }

    @PostMapping("/")
    public String findPatient(@ModelAttribute @Valid PeselView peselView, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "patientCard/patientCardError";
        } else {
            User user = userRepository.findByPesel(peselView.getPesel());
            if (user != null) {
                return "redirect:/card/" + user.getId();
            }
        }

        return "patientCard/patientCardFind";
    }

    @RequestMapping("/{patientId}")
    public String showCard(@PathVariable Long patientId, Model model) {

        Optional<User> userOptional = userRepository.findById(patientId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            return "patientCard/patientCardShow";
        }
        return "patientCard/patientCardError";
    }
}
