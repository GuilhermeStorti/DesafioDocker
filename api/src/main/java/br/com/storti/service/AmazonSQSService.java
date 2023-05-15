package br.com.storti.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonSQSService {

    private final AmazonSQS amazonSQS;

    public void sendMessage(String queueUrl, Object body){

        log.info("M sendMessage, queueUrl: {}, body: {}.", queueUrl, body);

        try {
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            amazonSQS.sendMessage(queueUrl, objectWriter.writeValueAsString(body));
        } catch (JsonProcessingException e) {
            log.error("M sendMessage, queueUrl: {}, body: {}. Error sending message", queueUrl, body);
            throw new RuntimeException(e);
        }
    }
}
