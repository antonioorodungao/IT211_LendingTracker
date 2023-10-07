package edu.mapua.it211.lendingtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/home")
    public String showHomePage(){
        System.out.println("Redirecting to home.");
        return "index";
    }


}
