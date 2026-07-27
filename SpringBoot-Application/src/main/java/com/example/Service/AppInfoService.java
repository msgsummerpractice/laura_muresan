package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AppProperties;

@Service
public class AppInfoService {
    
    @Autowired private final AppProperties appProperties;

    public AppInfoService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    private void validateAppProperties() {
        if (appProperties.getName() == null || appProperties.getName().isEmpty()) {
            throw new IllegalArgumentException("App name is not set in application properties.");
        }
        if (appProperties.getVersion() == null || appProperties.getVersion().isEmpty()) {
            throw new IllegalArgumentException("App version is not set in application properties.");
        }
    }
    
}
