package br.com.storti.exception;

public class BalanceException extends Exception {

    private String message;

    public BalanceException(String message) {
        super(message);
    }
}
