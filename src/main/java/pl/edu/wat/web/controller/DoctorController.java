package pl.edu.wat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping("/patientList")
    public String getPatientListPage(Model model){
        return "patientList";
    }
}
