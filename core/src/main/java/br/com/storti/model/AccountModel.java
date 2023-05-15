package br.com.storti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class AccountModel extends BaseModel {

    @Id
    @Column(name = "id_account")
    private Long id;

    @Column(name = "document_number")
    private String documentNumber;
}