package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Hello {
    @Bean
    public void sayHello() {
        System.out.println("Hello, Spring!");
    }
    
}
