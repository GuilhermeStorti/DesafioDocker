package br.com.storti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan({"br.com.storti.*"})
public class AsyncApp {

    public static void main(String[] args) {
        SpringApplication.run(AsyncApp.class, args);
    }
}