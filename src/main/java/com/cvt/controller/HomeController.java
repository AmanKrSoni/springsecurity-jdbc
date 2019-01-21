package com.cvt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping("/managers")
    public String showManager(){
        return "manager";
    }

    @RequestMapping("/admins")
    public String showAdmin(){
        return "admin";
    }
}
