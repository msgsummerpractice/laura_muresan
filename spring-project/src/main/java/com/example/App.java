package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@Configuration
@ComponentScan(basePackages = "com.example")
public class App {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
       // Hello hello = context.getBean(Hello.class);

        Folder folder = context.getBean(Folder.class);
        //System.out.println(hello.sayHello());
        folder.init();
        ((AnnotationConfigApplicationContext) context).close();
    }
}

