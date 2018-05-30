package pl.edu.wat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.dto.VisitDTO;
import pl.edu.wat.model.Disease;
import pl.edu.wat.model.PatientOnWard;
import pl.edu.wat.model.User;
import pl.edu.wat.model.Visit;
import pl.edu.wat.repository.PatientOnWardRepository;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.repository.VisitRepository;
import pl.edu.wat.service.UserService;
import pl.edu.wat.web.PeselView;
import pl.edu.wat.web.VisitView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/card")
public class PatientCardController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PatientOnWardRepository patientOnWardRepository;

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

        return "patientCard/patientCardError";
    }

    @RequestMapping("/{patientId}")
    public String showCard(@PathVariable Long patientId, Model model) {

        Optional<User> userOptional = userRepository.findById(patientId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Visit> visitList = user.getVisits();
            List<Disease> diseases = user.getDiseases();
            List<VisitDTO> historicalVisits = userService.getHistoricalVisits(user);

            String buttonText;
            PatientOnWard patientOnWard = patientOnWardRepository.findByPatientId(user.getId());
            if (patientOnWard == null) {
                buttonText = "";
            } else {
                buttonText = "Wypisz";
            }
            model.addAttribute("user", user);
            model.addAttribute("visits", historicalVisits);
            model.addAttribute("diseases", diseases);
            model.addAttribute("buttonText", buttonText);
            return "patientCard/patientCardShow";
        }
        return "patientCard/patientCardError";
    }

    @PostMapping("/newVisit/{patientId}")
    public String onNewVisit(@PathVariable long patientId, @ModelAttribute @Valid VisitView visitView,
                             BindingResult bindResult, Model model) {
//        model.addAttribute("patientId", patientId);

        if (bindResult.hasErrors()) {
            return "visit/addVisitValidationError";
        } else {
            if (userService.visitExist(visitView)) {
                return "visit/addVisitError";
            }
            userService.addNewVisit(visitView);
            return "patientCard/newVisit";
        }
    }

    @GetMapping("/newVisit/{patientId}")
    public String showNewVisit(@PathVariable long patientId, Model model) {
        model.addAttribute("patientId", patientId);

        return "patientCard/newVisit";
    }


    @RequestMapping("/patientRelease/{patientId}")
    public String releasePatient(@PathVariable long patientId) {

        PatientOnWard patientOnWard = patientOnWardRepository.findByPatientId(patientId);
        patientOnWardRepository.delete(patientOnWard);

        return "redirect:/card/{patientId}";
    }
}
