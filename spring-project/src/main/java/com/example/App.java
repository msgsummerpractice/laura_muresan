package com.example;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;




public class App {
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
        Hello hello = context.getBean(Hello.class);
        System.out.println(hello.sayHello());
        Folder folder = context.getBean(Folder.class);
        folder.init();
    }
}
}
