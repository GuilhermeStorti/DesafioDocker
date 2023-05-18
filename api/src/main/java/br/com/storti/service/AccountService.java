package br.com.storti.service;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.storti.exception.ServiceException;
import br.com.storti.mapper.AccountMapper;
import br.com.storti.model.AccountModel;
import br.com.storti.repository.AccountRepository;
import br.com.storti.dto.AccountConsultResponseDTO;
import br.com.storti.dto.AccountCreateRequestDTO;
import br.com.storti.dto.AccountCreateResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.storti.enums.AccountStatusEnum.ACTIVE;
import static br.com.storti.enums.AccountStatusEnum.INACTIVE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountCreateResponseDTO create(AccountCreateRequestDTO accountRequest) throws ServiceException {

        log.info("M create, account: {}", accountRequest);

        validateDocument(accountRequest.getDocumentNumber());

        Optional<AccountModel> accountOptional =
                accountRepository.findByDocumentNumberAndStatus(accountRequest.getDocumentNumber(), ACTIVE);
        if(accountOptional.isPresent()) {
            throw new ServiceException("Account already registered");
        }

        AccountModel model = AccountMapper.INSTANCE.requestDTOToModel(accountRequest);
        model.setStatus(ACTIVE);
        model.setBalance(0D);

        return AccountMapper.INSTANCE.modelToResponseDTO(accountRepository.save(model));
    }

    public AccountCreateResponseDTO disable(AccountCreateRequestDTO accountRequest) throws ServiceException {

        log.info("M disable, accountRequest: {}", accountRequest);

        AccountModel account = accountRepository.findByDocumentNumberAndStatus(accountRequest.getDocumentNumber(), ACTIVE)
                        .orElseThrow(() -> new ServiceException("Account not found"));
        account.setStatus(INACTIVE);

        return AccountMapper.INSTANCE.modelToResponseDTO(accountRepository.save(account));
    }

    public AccountConsultResponseDTO findById(Long accountId) throws ServiceException {

        log.info("M findById, accountId: {}", accountId);

        AccountModel account = accountRepository.findByIdAndStatus(accountId, ACTIVE)
                .orElseThrow(() -> new ServiceException("Account not found"));

        return AccountMapper.INSTANCE.modelToConsultResponseDTO(account);
    }

    private void validateDocument(Long documentNumber) throws ServiceException {

        String documentString = String.valueOf(documentNumber);
        if(documentString.length() < 11) {
            throw new ServiceException("Document invalid");
        }
        try {
            if (documentString.length() == 11) {
                CPFValidator cpfValidator = new CPFValidator();
                cpfValidator.assertValid(documentString);
            } else {
                CNPJValidator cnpjValidator = new CNPJValidator();
                cnpjValidator.assertValid(documentString);
            }
        } catch (InvalidStateException ex) {
            throw new ServiceException("Document invalid");
        }
    }
}
