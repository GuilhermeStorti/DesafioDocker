package br.com.storti.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonSNSService {

    private final AmazonSNS amazonSNS;

    public void sendMessage(String topic, Object body) {

        log.info("M sendMessage, topic: {}, body: {}", topic, body);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyString = mapper.writeValueAsString(body);
            amazonSNS.publish(topic, bodyString);
        } catch (Exception ex) {
            log.error("M sendMessage, topic: {}, body: {}, error: {}.", topic, body, ex.getMessage());
        }
    }
}
