package com.eqipped.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Success {

    @RequestMapping(value="/errorpage")
    public String errorpage() {
        System.out.println("Request for Error Page");
        return "error";
    }
    @RequestMapping(value = "/")
    public String successpage() {
        System.out.println("Request for Success Page");
        return "success";
    }
}
