package br.com.storti.resource;

import br.com.storti.service.AmazonSQSService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestResource {

    @Value("${aws.sqs.teste.url}")
    private String queueUrl;

    private final AmazonSQSService amazonSQSService;

    @GetMapping(path = "/sendMessage")
    public ResponseEntity<String> sendMessage() {
        amazonSQSService.sendMessage(queueUrl, "Teste do resource");
        return ResponseEntity.ok("Deu bom");
    }
}