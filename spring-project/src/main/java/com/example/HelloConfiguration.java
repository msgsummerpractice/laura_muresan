package com.example;

import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfiguration {

    public Hello hello() {
        return new Hello();
    }
    
}
