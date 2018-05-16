package pl.edu.wat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */

@Controller
public class MainController {
    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @RequestMapping("/main")
    @ResponseBody
    public String secured() {
        return "main";
    }
}
