package br.com.storti.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class JmsConfig {

    private final AmazonSQSAsync awsSqs;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer messageListenerContainer = simpleMessageListenerContainerFactory().createSimpleMessageListenerContainer();
        messageListenerContainer.setMessageHandler(queueMessageHandler());
        return messageListenerContainer;
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory() {
        SimpleMessageListenerContainerFactory messageListenerContainerFactory = new SimpleMessageListenerContainerFactory();
        messageListenerContainerFactory.setAmazonSqs(awsSqs);
        return messageListenerContainerFactory;
    }

    @Bean
    public QueueMessageHandler queueMessageHandler() {
        queueMessageHandlerFactory().setAmazonSqs(awsSqs);

        return queueMessageHandlerFactory().createQueueMessageHandler();
    }

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory() {
        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        org.springframework.messaging.converter.MappingJackson2MessageConverter jacksonMessageConverter = new org.springframework.messaging.converter.MappingJackson2MessageConverter();
        jacksonMessageConverter.setSerializedPayloadClass(String.class);
        jacksonMessageConverter.setStrictContentTypeMatch(false);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jacksonMessageConverter.setObjectMapper(objectMapper);

        PayloadMethodArgumentResolver payloadArgumentResolver = new PayloadMethodArgumentResolver(jacksonMessageConverter);
        factory.setArgumentResolvers(Collections.singletonList(payloadArgumentResolver));

        return factory;
    }

}
