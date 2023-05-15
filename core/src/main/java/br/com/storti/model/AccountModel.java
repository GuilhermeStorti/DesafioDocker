package br.com.storti.model;

import br.com.storti.enums.AccountStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "account")
public class AccountModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private Long id;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatusEnum status;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<TransactionModel> transactions;
}