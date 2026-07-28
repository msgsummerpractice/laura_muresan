package com.example.SpringBootData_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
   
    @GetMapping(value = "/login")
    public String Login(){
        return "login";
    }
}
