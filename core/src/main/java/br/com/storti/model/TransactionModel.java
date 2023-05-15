package br.com.storti.model;

import br.com.storti.enums.TransactionStatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class TransactionModel extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long id;

    @Column(name = "id_operation_type")
    private Long  operationType;

    @JoinColumn(name = "id_account")
    @ManyToOne
    private AccountModel account;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;
}
