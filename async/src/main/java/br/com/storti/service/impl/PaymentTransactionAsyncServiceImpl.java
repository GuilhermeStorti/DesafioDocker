package br.com.storti.service.impl;

import br.com.storti.model.TransactionModel;
import br.com.storti.service.TransactionPaymentAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentTransactionAsyncServiceImpl implements TransactionPaymentAsyncService {

    @Override
    public void pay(TransactionModel transaction) {

        log.info("Payment");
    }
}
