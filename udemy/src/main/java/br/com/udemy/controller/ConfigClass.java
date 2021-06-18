package br.com.udemy.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {

    @Bean
    public CommandLineRunner metodoInicial () {
        return args -> {
            System.out.println("metodoInicial executado...");
        };
    }
}
