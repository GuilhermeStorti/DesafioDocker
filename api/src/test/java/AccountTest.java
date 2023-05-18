import br.com.storti.dto.AccountConsultResponseDTO;
import br.com.storti.dto.AccountCreateRequestDTO;
import br.com.storti.dto.AccountCreateResponseDTO;
import br.com.storti.enums.AccountStatusEnum;
import br.com.storti.exception.ServiceException;
import br.com.storti.model.AccountModel;
import br.com.storti.repository.AccountRepository;
import br.com.storti.service.AccountService;
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
class AccountTest {

    private static final long DOCUMENT_NUMBER = 39773240800L;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void createAccountSuccessTest() throws ServiceException {

        AccountModel modelToSave = new AccountModel();
        modelToSave.setDocumentNumber(DOCUMENT_NUMBER);
        modelToSave.setStatus(AccountStatusEnum.ACTIVE);
        modelToSave.setBalance(0.0D);
        when(accountRepository.findByDocumentNumberAndStatus(DOCUMENT_NUMBER, AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.empty());

        AccountModel modelSaved = new AccountModel();
        modelSaved.setId(1L);
        modelSaved.setDocumentNumber(DOCUMENT_NUMBER);
        modelSaved.setStatus(AccountStatusEnum.ACTIVE);
        modelSaved.setBalance(0.0D);
        when(accountRepository.save(modelToSave)).thenReturn(modelSaved);

        AccountCreateResponseDTO responseDTO = accountService.create(new AccountCreateRequestDTO(DOCUMENT_NUMBER));
        Assertions.assertEquals(1L, responseDTO.getId());
    }

    @Test
    void createAccountDocumentRegisteredErrorTest() {

        AccountModel model = new AccountModel();
        model.setId(1L);
        when(accountRepository.findByDocumentNumberAndStatus(DOCUMENT_NUMBER, AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.of(model));

        Exception exception = assertThrows(ServiceException.class, () ->
                accountService.create(new AccountCreateRequestDTO(DOCUMENT_NUMBER)));
        Assertions.assertEquals("Account already registered", exception.getMessage());
    }

    @Test
    void createAccountDocumentInvalidTest() {
        Exception exception = assertThrows(ServiceException.class, () ->
                accountService.create(new AccountCreateRequestDTO(11111111111L)));
        Assertions.assertEquals("Document invalid", exception.getMessage());
    }

    @Test
    void disableAccountSuccessTest() throws ServiceException {
        AccountModel model = new AccountModel();
        model.setId(1L);
        when(accountRepository.findByDocumentNumberAndStatus(DOCUMENT_NUMBER, AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.of(model));

        AccountModel modelSaved = new AccountModel();
        modelSaved.setId(1L);
        when(accountRepository.save(model)).thenReturn(modelSaved);

        AccountCreateResponseDTO responseDTO = accountService.disable(new AccountCreateRequestDTO(DOCUMENT_NUMBER));
        Assertions.assertEquals(1L, responseDTO.getId());
    }

    @Test
    void disableAccountAccountNotFoundTest() {

        when(accountRepository.findByDocumentNumberAndStatus(DOCUMENT_NUMBER, AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(ServiceException.class, () ->
                accountService.disable(new AccountCreateRequestDTO(DOCUMENT_NUMBER)));
        Assertions.assertEquals("Account not found", exception.getMessage());
    }

    @Test
    void findAccountSuccessTest() throws ServiceException {

        AccountModel model = new AccountModel();
        model.setId(1L);
        model.setDocumentNumber(DOCUMENT_NUMBER);
        when(accountRepository.findByIdAndStatus(1L, AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.of(model));

        AccountConsultResponseDTO responseDTO = accountService.findById(1L);
        Assertions.assertEquals(DOCUMENT_NUMBER, responseDTO.getDocumentNumber());
        Assertions.assertEquals(1L, responseDTO.getId());
    }

    @Test
    void findAccountNotFoundTest() throws ServiceException {
        when(accountRepository.findByIdAndStatus(1L, AccountStatusEnum.ACTIVE))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(ServiceException.class, () ->
                accountService.findById(1L));
        Assertions.assertEquals("Account not found", exception.getMessage());
    }
}
