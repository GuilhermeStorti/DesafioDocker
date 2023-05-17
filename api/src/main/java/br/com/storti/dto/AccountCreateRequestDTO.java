package br.com.storti.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreateRequestDTO {

    @JsonProperty("document_number")
    private Long documentNumber;
}
