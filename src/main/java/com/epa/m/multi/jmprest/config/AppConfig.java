package com.epa.m.multi.jmprest.config;

import com.epa.m.multi.jmprest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CommandLineRunner initializeDB(@Autowired UserService service) {
        return args -> service.initializeDatabase();
    }
}