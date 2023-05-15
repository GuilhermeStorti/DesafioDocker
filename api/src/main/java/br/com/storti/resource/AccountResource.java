package br.com.storti.resource;

import br.com.storti.service.AccountService;
import dto.AccountCreateRequestDTO;
import dto.AccountCreateResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountResource {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountCreateResponseDTO> createAccount(@RequestBody AccountCreateRequestDTO account) {

        log.info("M createAccount, account {}", account);

        return ResponseEntity.ok(accountService.create(account));
    }
}
