package br.com.storti.desafiopismo.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestResource {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Deu bom!");
    }
}