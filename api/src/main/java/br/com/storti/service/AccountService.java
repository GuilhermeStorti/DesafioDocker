package br.com.storti.service;

import br.com.storti.mapper.AccountMapper;
import br.com.storti.model.AccountModel;
import br.com.storti.repository.AccountRepository;
import dto.AccountCreateRequestDTO;
import dto.AccountCreateResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.storti.enums.AccountStatusEnum.ACTIVE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    public AccountCreateResponseDTO create(AccountCreateRequestDTO accountRequest) {

        log.info("M create, account: {}", accountRequest);

        AccountModel model = AccountMapper.INSTANCE.requestDTOToModel(accountRequest);
        model.setStatus(ACTIVE);

        AccountCreateResponseDTO response = AccountMapper.INSTANCE.modelToResponseDTO(accountRepository.save(model));

        return response;
    }
}
