package br.com.storti.service.impl;

import br.com.storti.enums.TransactionStatusEnum;
import br.com.storti.model.TransactionModel;
import br.com.storti.repository.TransactionRepository;
import br.com.storti.service.TransactionPaymentAsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnSightTransactionAsyncServiceImpl implements TransactionPaymentAsyncService {

    private final TransactionRepository transactionRepository;

    @Override
    public void pay(TransactionModel transaction) {


        log.info("M pay, transactionId: {}, accountId: {}. OnSight async process start.", transaction.getId(), transaction.getAccount().getId());

        // Regras de negócio do produto
        transaction.setStatus(TransactionStatusEnum.SUCCESS);
        transactionRepository.save(transaction);
    }
}
