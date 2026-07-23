package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Hello {
    @Bean
    public String sayHello() {
        return "Hello, Spring!";
    }
    
}
