package br.com.storti.resource;

import br.com.storti.exception.ServiceException;
import dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ControllerAdviceResource {

    @ExceptionHandler({ServiceException.class})
    @ResponseBody
    public ErrorDTO serviceError(ServiceException e) {

        log.error("M serviceError, error message: {}", e.getMessage());
        return ErrorDTO.builder().message(e.getMessage()).code(100).build();
    }
}
