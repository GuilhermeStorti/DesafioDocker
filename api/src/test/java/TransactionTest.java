import br.com.storti.dto.AccountCreateRequestDTO;
import br.com.storti.dto.TransactionRequestDTO;
import br.com.storti.dto.TransactionResponseDTO;
import br.com.storti.enums.AccountStatusEnum;
import br.com.storti.enums.TransactionStatusEnum;
import br.com.storti.exception.BalanceException;
import br.com.storti.exception.ServiceException;
import br.com.storti.model.AccountModel;
import br.com.storti.model.OperationTypeModel;
import br.com.storti.model.TransactionModel;
import br.com.storti.repository.AccountRepository;
import br.com.storti.repository.OperationTypeRepository;
import br.com.storti.repository.TransactionRepository;
import br.com.storti.service.AmazonSQSService;
import br.com.storti.service.TransactionService;
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
public class TransactionTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    OperationTypeRepository operationTypeRepository;

    @Mock
    AmazonSQSService amazonSQSService;

    @Test
    void createTransactionNullAmountTest() throws ServiceException, BalanceException {

        Exception exception = assertThrows(ServiceException.class, () ->
                transactionService.create(new TransactionRequestDTO()));
        Assertions.assertEquals("Transaction amount is invalid", exception.getMessage());
    }

    @Test
    void createTransactionZeroAmountTest() throws ServiceException, BalanceException {

        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setAmount(-2D);
        Exception exception = assertThrows(ServiceException.class, () ->
                transactionService.create(requestDTO));
        Assertions.assertEquals("Transaction amount is invalid", exception.getMessage());
    }

    @Test
    void createTransactionInvalidAccountTest() throws ServiceException, BalanceException {

        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setAccountId(1L);
        requestDTO.setAmount(1D);

        when(accountRepository.findByIdAndStatus(requestDTO.getAccountId(), AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(ServiceException.class, () ->
                transactionService.create(requestDTO));
        Assertions.assertEquals("Account not found or not active", exception.getMessage());
    }

    @Test
    void createTransactionInvalidOperationTypeTest() throws ServiceException, BalanceException {

        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setAccountId(1L);
        requestDTO.setAmount(1D);
        requestDTO.setOperationTypeId(10L);

        when(accountRepository.findByIdAndStatus(requestDTO.getAccountId(), AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.of(getValidAccount()));
        when(operationTypeRepository.findById(10L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ServiceException.class, () ->
                transactionService.create(requestDTO));
        Assertions.assertEquals("OperationType not found", exception.getMessage());
    }

    @Test
    void createTransactionInsufficientBalanceError() {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setAccountId(1L);
        requestDTO.setAmount(100D);
        requestDTO.setOperationTypeId(10L);

        when(accountRepository.findByIdAndStatus(requestDTO.getAccountId(), AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.of(getValidAccount()));
        when(operationTypeRepository.findById(10L))
                .thenReturn(Optional.of(getValidOperationalType()));
        when(transactionRepository.save(getNewSavedTransaction(requestDTO.getAmount())))
                .thenReturn(getNewSavedTransaction(requestDTO.getAmount()));

        Exception exception = assertThrows(BalanceException.class, () ->
                transactionService.create(requestDTO));
        Assertions.assertEquals("Insufficient balance.", exception.getMessage());
    }

    @Test
    void createTransactionSuccess() throws ServiceException, BalanceException {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setAccountId(1L);
        requestDTO.setAmount(1D);
        requestDTO.setOperationTypeId(10L);

        when(accountRepository.findByIdAndStatus(requestDTO.getAccountId(), AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.of(getValidAccount()));
        when(operationTypeRepository.findById(10L))
                .thenReturn(Optional.of(getValidOperationalType()));
        when(transactionRepository.save(getNewSavedTransaction(requestDTO.getAmount())))
                .thenReturn(getNewSavedTransaction(requestDTO.getAmount()));

        TransactionResponseDTO responseDTO = transactionService.create(requestDTO);

        Assertions.assertEquals("Transaction in process", responseDTO.getMessage());
    }

    private TransactionModel getNewSavedTransaction(Double amount) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setAccount(getValidAccount());
        transactionModel.setOperationType(getValidOperationalType());
        transactionModel.setAmount(amount);
        transactionModel.setStatus(TransactionStatusEnum.ASYNC_PROCESS);
        return transactionModel;
    }

    private OperationTypeModel getValidOperationalType() {
        OperationTypeModel operationTypeModel = new OperationTypeModel();
        operationTypeModel.setDescription("COMPRA A VISTA");
        operationTypeModel.setId(1);
        return operationTypeModel;
    }

    private AccountModel getValidAccount() {
        AccountModel accountModel = new AccountModel();
        accountModel.setDocumentNumber("1");
        accountModel.setBalance(10D);
        accountModel.setStatus(AccountStatusEnum.ACTIVE);
        return accountModel;
    }
}
