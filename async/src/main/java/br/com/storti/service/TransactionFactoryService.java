package br.com.storti.service;

import br.com.storti.model.OperationTypeModel;
import br.com.storti.service.impl.InstallmentTransactionAsyncServiceImpl;
import br.com.storti.service.impl.OnSightTransactionAsyncServiceImpl;
import br.com.storti.service.impl.PaymentTransactionAsyncServiceImpl;
import br.com.storti.service.impl.WithdrawTransactionAsyncServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionFactoryService {

    private final InstallmentTransactionAsyncServiceImpl installmentTransactionAsyncService;
    private final OnSightTransactionAsyncServiceImpl onSightTransactionAsyncService;
    private final PaymentTransactionAsyncServiceImpl paymentTransactionAsyncService;
    private final WithdrawTransactionAsyncServiceImpl withdrawTransactionAsyncService;

    public TransactionPaymentAsyncService getService(OperationTypeModel operationTypeModel) {

        log.info("M getService, operationTypeModel {}", operationTypeModel);

        return switch (operationTypeModel.getId()) {
            case 1 -> onSightTransactionAsyncService;
            case 2 -> installmentTransactionAsyncService;
            case 3 -> withdrawTransactionAsyncService;
            case 4 -> paymentTransactionAsyncService;
            default -> null;
        };
    }
}
