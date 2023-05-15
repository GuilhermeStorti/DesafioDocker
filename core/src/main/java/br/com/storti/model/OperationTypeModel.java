package br.com.storti.model;

import jakarta.persistence.*;

@Entity
@Table(name = "operation_type")
public class OperationTypeModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operation_type")
    private Long id;

    @Column(name = "description")
    private String description;
}
