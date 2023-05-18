package br.com.storti.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionRequestDTO {

    @JsonProperty(value = "account_id")
    private Long accountId;
    @JsonProperty(value = "operation_type_id")
    private Long operationTypeId;
    @JsonProperty(value = "amount")
    private Double amount;
}
