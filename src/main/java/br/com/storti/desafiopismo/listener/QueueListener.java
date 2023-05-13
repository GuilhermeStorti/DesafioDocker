package br.com.storti.desafiopismo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueListener {

    @SqsListener("${aws.sqs.teste.url}")
    public void testQueueListener(String body){
        log.info("M testQueueListener, body: {}. Message received", body);
    }
}
