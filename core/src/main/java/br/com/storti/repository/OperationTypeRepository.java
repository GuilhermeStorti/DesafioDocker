package br.com.storti.repository;

import br.com.storti.model.OperationTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationTypeModel, Long> {
}
