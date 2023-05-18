package br.com.storti.resource;

import br.com.storti.exception.BalanceException;
import br.com.storti.exception.ServiceException;
import br.com.storti.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ControllerAdviceResource {

    @ExceptionHandler({ServiceException.class})
    @ResponseBody
    public ResponseEntity<ErrorDTO> serviceError(ServiceException e) {

        log.error("M serviceError, error message: {}", e.getMessage());
        ErrorDTO response = ErrorDTO.builder().message(e.getMessage()).code(100).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BalanceException.class})
    @ResponseBody
    public ResponseEntity<ErrorDTO> serviceError(BalanceException e) {

        log.error("M serviceError, error message: {}", e.getMessage());
        ErrorDTO response = ErrorDTO.builder().message(e.getMessage()).code(101).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
