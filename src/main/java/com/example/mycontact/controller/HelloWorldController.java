package com.example.mycontact.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/*
@Controller
@Responsebody
 */
public class HelloWorldController {
    @GetMapping(value = "/api/helloWorld")
    public String helloWorld() {
        return "Hello World";
    }
}
