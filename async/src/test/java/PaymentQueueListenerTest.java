import br.com.storti.TransactionNotFoundException;
import br.com.storti.enums.AccountStatusEnum;
import br.com.storti.enums.TransactionStatusEnum;
import br.com.storti.exception.AsyncPaymentException;
import br.com.storti.listener.PaymentQueueListener;
import br.com.storti.model.AccountModel;
import br.com.storti.model.OperationTypeModel;
import br.com.storti.model.TransactionModel;
import br.com.storti.repository.TransactionRepository;
import br.com.storti.service.TransactionFactoryService;
import br.com.storti.service.impl.InstallmentTransactionAsyncServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentQueueListenerTest {

    @InjectMocks
    PaymentQueueListener paymentQueueListener;

    @Mock
    TransactionFactoryService transactionFactoryService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    InstallmentTransactionAsyncServiceImpl installmentTransactionAsyncService;

    @Test
    void transactionNotFoundExceptionTest() {

        when(transactionRepository.findById(1L))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(TransactionNotFoundException.class, () ->
                paymentQueueListener.paymentQueueListener(1L));
        Assertions.assertEquals("Transaction not found for id: 1", exception.getMessage());
    }

    @Test
    void transactionWrongStatusExceptionTest() {

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setStatus(TransactionStatusEnum.ERROR);
        transactionModel.setId(1L);
        transactionModel.setAccount(getValidAccount());

        when(transactionRepository.findById(1L))
                .thenReturn(Optional.of(transactionModel));

        Exception exception = assertThrows(AsyncPaymentException.class, () ->
                paymentQueueListener.paymentQueueListener(1L));
        Assertions.assertEquals("Transaction not in ASYNC_PROCESS status", exception.getMessage());
    }

    @Test
    void transactionSuccessTest() throws AsyncPaymentException, TransactionNotFoundException {

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setStatus(TransactionStatusEnum.ASYNC_PROCESS);
        transactionModel.setId(1L);
        transactionModel.setAccount(getValidAccount());
        transactionModel.setOperationType(getValidOperationalType());

        when(transactionRepository.findById(1L))
                .thenReturn(Optional.of(transactionModel));
        when(transactionFactoryService.getService(getValidOperationalType()))
                .thenReturn(installmentTransactionAsyncService);

        paymentQueueListener.paymentQueueListener(1L);
    }

    private OperationTypeModel getValidOperationalType() {
        OperationTypeModel operationTypeModel = new OperationTypeModel();
        operationTypeModel.setDescription("COMPRA A VISTA");
        operationTypeModel.setId(1);
        return operationTypeModel;
    }

    private AccountModel getValidAccount() {
        AccountModel accountModel = new AccountModel();
        accountModel.setDocumentNumber(1L);
        accountModel.setBalance(10D);
        accountModel.setStatus(AccountStatusEnum.ACTIVE);
        return accountModel;
    }

}
