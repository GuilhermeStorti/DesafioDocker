package br.com.storti.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentQueueListener {

    @SqsListener("${aws.sqs.payment-queue.url}")
    public void paymentQueueListener(String body){
        log.info("M paymentQueueListener, body: {}. Message received", body);
    }
}
