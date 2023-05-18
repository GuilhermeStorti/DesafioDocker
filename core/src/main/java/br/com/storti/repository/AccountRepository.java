package br.com.storti.repository;

import br.com.storti.enums.AccountStatusEnum;
import br.com.storti.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    Optional<AccountModel> findByDocumentNumberAndStatus(String documentNumber, AccountStatusEnum status);

    Optional<AccountModel> findByIdAndStatus(Long id, AccountStatusEnum status);
}
