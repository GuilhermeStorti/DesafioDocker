package br.com.storti.model;

import br.com.storti.enums.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long id;

    @JoinColumn(name = "id_operation_type")
    @ManyToOne
    private OperationTypeModel operationType;

    @JoinColumn(name = "id_account")
    @ManyToOne
    private AccountModel account;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;
}
