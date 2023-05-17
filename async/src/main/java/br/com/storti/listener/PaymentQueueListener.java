package br.com.storti.listener;

import br.com.storti.TransactionNotFoundException;
import br.com.storti.enums.TransactionStatusEnum;
import br.com.storti.model.TransactionModel;
import br.com.storti.repository.TransactionRepository;
import br.com.storti.service.TransactionFactoryService;
import br.com.storti.service.TransactionPaymentAsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentQueueListener {

    private final TransactionRepository transactionRepository;
    private final TransactionFactoryService transactionFactoryService;

    @SqsListener("${aws.sqs.payment-queue.url}")
    public void paymentQueueListener(Long transactionId) throws TransactionNotFoundException {
        log.info("M paymentQueueListener, transactionId: {}. Message received", transactionId);

        //TODO Resolver bug na consulta account não está carregando

        TransactionModel transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found for id: " + transactionId));

        if(transaction.getStatus() != TransactionStatusEnum.ASYNC_PROCESS) {
            log.error("M paymentQueueListener, transactionId: {}, transactionStatus: {}, accountId: {}. Transaction not in ASYNC_PROCESS status."
                    , transaction.getId(), transaction.getStatus(), transaction.getAccount().getId());
        }

        TransactionPaymentAsyncService service = transactionFactoryService.getService(transaction.getOperationType());

        service.pay(transaction);
    }
}
