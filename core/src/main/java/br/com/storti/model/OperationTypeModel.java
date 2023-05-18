package br.com.storti.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "operation_type")
public class OperationTypeModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operation_type")
    private int id;

    @Column(name = "description")
    private String description;
}
