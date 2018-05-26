package pl.edu.wat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wat.security.UserDetailsProvider;
import pl.edu.wat.service.UserService;
import pl.edu.wat.service.VisitService;

@Controller
public class VisitController {

    @Autowired
    private UserService userService;

    @Autowired
    private VisitService visitService;

    @GetMapping("/confirm")
    public String confirmVisit(@RequestParam long id, Model model) {
        model.addAttribute("doctor", userService.getDoctorByVisit(id));
        model.addAttribute("visit", visitService.getVisit(id));
        return "visit/confirmVisit";
    }

    @GetMapping("/bookvisit")
    public String bookVisit(@RequestParam long doctorId, @RequestParam long visitId, Model model) {
        if(visitService.bookVisitSuccesfully(visitId))
            return "redirect:details?id=" + doctorId;
        else{
            model.addAttribute("doctor", userService.findUser(doctorId));
            return "visit/confirmVisitError";
        }
    }
}
