package br.com.storti.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransactionStatusEnum {

    RECEIVED("RECEIVED"),
    ASYNC_PROCESS("ASYNC_PROCESS"),
    SUCCESS("SUCCESS"),
    ERROR("SUCCESS");

    private final String description;
}