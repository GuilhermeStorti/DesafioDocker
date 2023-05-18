package br.com.storti;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionNotFoundException extends Exception {

    private String message;
}
