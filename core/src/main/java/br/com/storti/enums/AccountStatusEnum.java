package br.com.storti.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountStatusEnum {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String description;
}
