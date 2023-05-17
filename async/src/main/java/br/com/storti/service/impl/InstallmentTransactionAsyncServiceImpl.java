package br.com.storti.service.impl;

import br.com.storti.dto.UserNotificationDTO;
import br.com.storti.enums.TransactionStatusEnum;
import br.com.storti.model.TransactionModel;
import br.com.storti.repository.TransactionRepository;
import br.com.storti.service.AmazonSNSService;
import br.com.storti.service.TransactionPaymentAsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstallmentTransactionAsyncServiceImpl implements TransactionPaymentAsyncService {

    @Value("${aws.sns-client-notification.arn}")
    private String snsClientNotificationArn;

    private final TransactionRepository transactionRepository;
    private final AmazonSNSService amazonSNSService;

    @Override
    public void pay(TransactionModel transaction) {


        log.info("M pay, transactionId: {}, accountId: {}. Installment async process start.", transaction.getId(), transaction.getAccount().getId());

        // Regras de negócio do produto
        transaction.setStatus(TransactionStatusEnum.SUCCESS);
        transactionRepository.save(transaction);

        UserNotificationDTO userNotification = UserNotificationDTO.builder()
                .message(String.format("Sua transação %s no valor de %s foi efetivada com sucesso!",
                        transaction.getOperationType().getDescription(), transaction.getAmount())).build();
        amazonSNSService.sendMessage(snsClientNotificationArn, userNotification);
    }
}
