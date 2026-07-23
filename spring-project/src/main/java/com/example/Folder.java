package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Folder {
    private final File file1;
    private final File file2;

    @Autowired public Folder(@Qualifier("file1") File file1,
                             @Qualifier("file2") File file2) {
        this.file1 = file1;
        this.file2 = file2;
    }

    public void init() {
        file1.init();
        file2.init();
        System.out.println("File1 is initialized.");
        System.out.println("File2 is initialized.");
        
    }
}
