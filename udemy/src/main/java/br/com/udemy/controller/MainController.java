package br.com.udemy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Value("${application.name}")
    private String appName;

    @GetMapping
    public String applicationName () {
        return this.appName;
    }
}
