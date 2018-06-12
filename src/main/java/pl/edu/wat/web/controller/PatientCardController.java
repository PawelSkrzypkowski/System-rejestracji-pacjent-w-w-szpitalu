package pl.edu.wat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.wat.dto.VisitDTO;
import pl.edu.wat.model.*;
import pl.edu.wat.repository.ExaminationRepository;
import pl.edu.wat.repository.PatientOnWardRepository;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.repository.VisitRepository;
import pl.edu.wat.service.UserService;
import pl.edu.wat.web.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
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

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    ExaminationRepository examinationRepository;

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
            List<Disease> diseases = user.getDiseases();
            List<VisitDTO> historicalVisits = userService.getVisits(user);
            List<Examination> examinations = user.getExaminations();
            

            String buttonTextRelease;
            String buttonTextAdmision;
            String buttonTextExamination;
            PatientOnWard patientOnWard = patientOnWardRepository.findByPatientId(user.getId());
            if (patientOnWard == null) {
                buttonTextAdmision = "Przyjmij na oddział";
                buttonTextRelease = "";
                buttonTextExamination = "";
            } else {
                buttonTextAdmision = "";
                buttonTextRelease = "Wypisz";
                buttonTextExamination = "Nowe Badanie";
            }
            model.addAttribute("patientId", patientId);
            model.addAttribute("user", user);
            model.addAttribute("visits", historicalVisits);
            model.addAttribute("diseases", diseases);
            model.addAttribute("examinations", examinations);
            model.addAttribute("buttonTextRelease", buttonTextRelease);
            model.addAttribute("buttonTextAdmision", buttonTextAdmision);
            model.addAttribute("buttonTextExamination", buttonTextExamination);
            return "patientCard/patientCardShow";
        }
        return "patientCard/patientCardError";
    }

    @PostMapping("/newVisitAdded/{patientId}")
    public String onNewVisit(@PathVariable long patientId, @ModelAttribute @Valid VisitWithDescriptionView visitWithDescriptionView,
                             BindingResult bindResult, Model model) {

        if (bindResult.hasErrors()) {
            return "patientCard/addVisitValidationError";
        } else {
            if (userService.visitExist(visitWithDescriptionView)) {
                return "patientCard/addVisitError";
            }
            userService.addNewVisitWithUserId(visitWithDescriptionView, patientId);
            return "redirect:/card/{patientId}";
        }
    }

    @PostMapping("/visitEdited/{patientId}")
    public String onVisitEdited(@PathVariable long patientId, @RequestParam Long visitId, @ModelAttribute @Valid VisitDescriptionView visitDescriptionView,
                                BindingResult bindResult, Model model) {

        if (bindResult.hasErrors()) {
            return "patientCard/addVisitValidationError";
        } else {
            Visit visit = visitRepository.getOne(visitId);
            visit.setDescription(visitDescriptionView.getDescription());
            visitRepository.save(visit);
            return "redirect:/card/{patientId}";
        }
    }


    @RequestMapping("/editVisit")
    public String editVisit(Model model, @RequestParam Long patientId, @RequestParam Long visitId) {
        Visit visit = visitRepository.getOne(visitId);
        model.addAttribute("visit", visit);
        model.addAttribute("patientId", patientId);

        return "patientCard/editVisit";
    }

    @GetMapping("/newVisit/{patientId}")
    public String showNewVisit(@PathVariable long patientId, Model model) {
        model.addAttribute("patientId", patientId);

        return "patientCard/newVisit";
    }

    @RequestMapping("/newDisease")
    public String newDisease(@RequestParam Long patientId, Model model) {

        model.addAttribute("patientId", patientId);
        return "patientCard/newDisease";
    }

    @RequestMapping("/newDiseaseAdded")
    public String addNewDisease(@RequestParam Long patientId, @ModelAttribute @Valid DiseaseView diseaseView, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "diseaseError";
        } else {
            userService.addDisease(patientId, diseaseView);
        }


        redirectAttributes.addAttribute("patientId", patientId);
        return "redirect:/card/{patientId}";
    }


    @RequestMapping("/patientRelease/{patientId}")
    public String releasePatient(HttpServletResponse response, @PathVariable long patientId) {

        PatientOnWard patientOnWard = patientOnWardRepository.findByPatientId(patientId);
        //TODO zapisanie do pliku

        patientOnWardRepository.delete(patientOnWard);

        return "redirect:/card/{patientId}";
    }

    @PostMapping("/patientAdmission/{patientId}")
    public String admitPatient(@PathVariable long patientId, Model model) {

        model.addAttribute("patientId", patientId);

        System.out.println("/card/patientAdmission/ POST");

        return "patientCard/patientAdmission";
    }

    @PostMapping("/patientAdmission/")
    public String onPatientAdmitted(@ModelAttribute @Valid AdmissionView admissionView, BindingResult bindingResult, @RequestParam Long patientId, RedirectAttributes redirectAttributes) {

        System.out.println("/card/patientAdmission/ GET");
        System.out.println("PID" + patientId);
        if (bindingResult.hasErrors()) {
            return "patientCard/patientAdmissionError";
        } else {

            userService.addPatientOnWard(admissionView, patientId);
            System.out.println("Dodano");
        }

        redirectAttributes.addAttribute("patientId", patientId);

        return "redirect:/card/{patientId}";
    }


    @PostMapping("/newExamination/{patientId}")
    public String addExamination(@PathVariable long patientId, Model model) {

        model.addAttribute("patientId", patientId);

        return "patientCard/newExamination";
    }

    @PostMapping("/newExaminationAdded")
    public String onNewExaminationAdded(@ModelAttribute @Valid ExaminationView examinationView, BindingResult bindingResult, @RequestParam Long patientId, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "patientCard/addingExaminationError";
        } else {
            userService.addExamination(patientId, examinationView);
        }

        redirectAttributes.addAttribute("patientId", patientId);

        return "redirect:/card/{patientId}";
    }


}