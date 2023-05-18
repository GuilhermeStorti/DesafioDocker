package br.com.storti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan({"br.com.storti.*"})
public class ApiApp {

    public static void main(String[] args) {
        SpringApplication.run(ApiApp.class, args);
    }
}