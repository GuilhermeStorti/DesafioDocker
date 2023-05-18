package br.com.storti.resource;

import br.com.storti.exception.BalanceException;
import br.com.storti.exception.ServiceException;
import br.com.storti.dto.TransactionRequestDTO;
import br.com.storti.dto.TransactionResponseDTO;
import br.com.storti.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionResource {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestBody TransactionRequestDTO transactionRequestDTO) throws ServiceException, BalanceException {

        log.info("M create, transactionRequestDTO: {}", transactionRequestDTO);
        return new ResponseEntity<>(transactionService.create(transactionRequestDTO), HttpStatus.CREATED);
    }
}
