package br.com.storti.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountCreateRequestDTO {

    @JsonProperty("document_number")
    private Long documentNumber;
}
