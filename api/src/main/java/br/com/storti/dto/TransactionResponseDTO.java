package br.com.storti.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponseDTO {

    private String message;
}
