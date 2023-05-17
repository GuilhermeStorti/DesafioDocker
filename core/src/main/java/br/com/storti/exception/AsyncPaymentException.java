package br.com.storti.exception;

public class AsyncPaymentException extends Exception {

    private String message;

    public AsyncPaymentException(String message) {
        super(message);
    }
}
