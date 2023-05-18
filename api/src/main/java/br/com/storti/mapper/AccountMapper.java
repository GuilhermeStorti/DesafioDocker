package br.com.storti.mapper;

import br.com.storti.model.AccountModel;
import br.com.storti.dto.AccountConsultResponseDTO;
import br.com.storti.dto.AccountCreateRequestDTO;
import br.com.storti.dto.AccountCreateResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountModel requestDTOToModel(AccountCreateRequestDTO accountCreateRequestDTO);
    AccountCreateResponseDTO modelToResponseDTO(AccountModel accountModel);
    AccountConsultResponseDTO modelToConsultResponseDTO(AccountModel accountModel);
}
