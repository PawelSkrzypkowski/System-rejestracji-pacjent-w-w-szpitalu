package pl.edu.wat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wat.model.Disease;
import pl.edu.wat.model.PatientOnWard;
import pl.edu.wat.model.User;
import pl.edu.wat.model.UserRole;
import pl.edu.wat.repository.PatientOnWardRepository;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PatientOnWardRepository patientOnWardRepository;

    @Autowired
    UserService userService;

    @GetMapping("/patientList")
    public String getPatientListPage(Model model){
        List<User> patients = userRepository.findAll().stream().filter(p -> {
            Set<UserRole> userRoles = p.getRoles();
            for(UserRole ur : userRoles){
                if(ur.getRole().equalsIgnoreCase("ROLE_USER"))
                    return true;
            }
            return false;
        }).collect(Collectors.toList());
        List<PatientOnWard> patientOnWardList = new ArrayList<>();
        List<Disease> diseaseList = new ArrayList<>();
        for(User p : patients){
            patientOnWardList.add(patientOnWardRepository.findByPatientId(p.getId()));
            if(p.getDiseases().size() > 0){
                diseaseList.add(p.getDiseases().get(p.getDiseases().size() - 1));
            } else {
                diseaseList.add(null);
            }
        }
        model.addAttribute("patients", patients);
        model.addAttribute("patientsOnWard", patientOnWardList);
        model.addAttribute("diseases", diseaseList);
        return "patientList";
    }

    @GetMapping("/visits")
    public String getDoctorSchedule( Model model){
        model.addAttribute("visits",userService.getDoctorFutureVisits());
        return "visit/doctorVisits";
    }

    @GetMapping("/removeVisit")
    public String removeDoctorVisit(@RequestParam long id, Model model){
        userService.removeVisit(id);
        return  "redirect:/doctor/visits";
    }
}
