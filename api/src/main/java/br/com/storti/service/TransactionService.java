package br.com.storti.service;

import br.com.storti.enums.AccountStatusEnum;
import br.com.storti.enums.TransactionStatusEnum;
import br.com.storti.exception.ServiceException;
import br.com.storti.model.AccountModel;
import br.com.storti.model.OperationTypeModel;
import br.com.storti.model.TransactionModel;
import br.com.storti.repository.AccountRepository;
import br.com.storti.repository.OperationTypeRepository;
import br.com.storti.repository.TransactionRepository;
import br.com.storti.dto.TransactionRequestDTO;
import br.com.storti.dto.TransactionResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    @Value("${aws.sqs.payment-queue.url}")
    private String paymentQueueUrl;

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;
    private final AmazonSQSService amazonSQSService;

    public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) throws ServiceException {

        //TODO tratar transacao de valor negativo

        log.info("M create, transactionRequestDTO: {}", transactionRequestDTO);

        AccountModel account = accountRepository.findByIdAndStatus(transactionRequestDTO.getAccountId(), AccountStatusEnum.ACTIVE)
                .orElseThrow(() -> new ServiceException("Account not found or not active"));

        OperationTypeModel operation = operationTypeRepository.findById(transactionRequestDTO.getOperationTypeId())
                .orElseThrow(() -> new ServiceException("OperationType not found"));

        TransactionModel transactionModel = TransactionModel.builder()
                .account(account)
                .operationType(operation)
                .amount(transactionRequestDTO.getAmount())
                .status(TransactionStatusEnum.ASYNC_PROCESS)
                .build();

        TransactionModel response = transactionRepository.save(transactionModel);

        amazonSQSService.sendMessage(paymentQueueUrl, response.getId());

        return TransactionResponseDTO.builder().message("Transaction in process").build();
    }
}
