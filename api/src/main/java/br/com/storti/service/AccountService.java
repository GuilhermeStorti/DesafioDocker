package br.com.storti.service;

import br.com.storti.model.AccountModel;
import br.com.storti.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    public AccountModel save(AccountModel accountModel) {

        return accountRepository.save(accountModel);
    }
}
