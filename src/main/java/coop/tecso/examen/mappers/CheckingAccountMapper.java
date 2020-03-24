package coop.tecso.examen.mappers;

import coop.tecso.examen.dto.CheckingAccountDTO;
import coop.tecso.examen.dto.MovementDTO;
import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Movement;

public class CheckingAccountMapper {
    public static CheckingAccount toEntity(CheckingAccountDTO dto){
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setBalance(dto.getBalance());
        checkingAccount.setCurrency(dto.getCurrency());
        checkingAccount.setId(dto.getId());
        return checkingAccount;
    }

    public static CheckingAccountDTO toDTO(CheckingAccount checkingAccount){
        CheckingAccountDTO checkingAccountDTO = new CheckingAccountDTO();
        checkingAccountDTO.setBalance(checkingAccount.getBalance());
        checkingAccountDTO.setCurrency(checkingAccount.getCurrency());
        checkingAccountDTO.setId(checkingAccount.getId());
        return checkingAccountDTO;
    }
}
