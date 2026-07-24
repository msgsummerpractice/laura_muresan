package com.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    @Qualifier("file1")
    public File getFile1(){
        return new File();
    }

    @Bean
    @Qualifier("file2")
    public File getFile2(){
        return new File();
    }
    
}