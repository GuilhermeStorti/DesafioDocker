package br.com.storti.resource;

import br.com.storti.exception.ServiceException;
import br.com.storti.dto.AccountConsultResponseDTO;
import br.com.storti.dto.AccountCreateRequestDTO;
import br.com.storti.dto.AccountCreateResponseDTO;
import br.com.storti.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountResource {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountCreateResponseDTO> createAccount(@RequestBody AccountCreateRequestDTO account) throws ServiceException {

        log.info("M createAccount, account {}", account);
        return ResponseEntity.ok(accountService.create(account));
    }

    @DeleteMapping
    public ResponseEntity<AccountCreateResponseDTO> disableAccount(@RequestBody AccountCreateRequestDTO account) throws ServiceException {

        log.info("M disableAccount, account {}", account);
        return ResponseEntity.ok(accountService.disable(account));
    }

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<AccountConsultResponseDTO> findById(@RequestParam(name = "accountId") Long accountId) throws ServiceException {

        log.info("M findById, accountId {}", accountId);
        return ResponseEntity.ok(accountService.findById(accountId));
    }
}
