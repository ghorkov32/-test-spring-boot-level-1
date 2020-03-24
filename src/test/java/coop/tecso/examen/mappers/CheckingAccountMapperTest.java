package coop.tecso.examen.mappers;

import coop.tecso.examen.dto.CheckingAccountDTO;
import coop.tecso.examen.dto.MovementDTO;
import coop.tecso.examen.enums.Currency;
import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Movement;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class CheckingAccountMapperTest {

    @Test
    public void toEntity() {
        CheckingAccountDTO checkingAccountDTO = new CheckingAccountDTO();
        checkingAccountDTO.setBalance(BigDecimal.TEN);
        checkingAccountDTO.setCurrency(Currency.ARS);
        checkingAccountDTO.setId(1L);

        CheckingAccount checkingAccount = CheckingAccountMapper.toEntity(checkingAccountDTO);

        assertEquals(BigDecimal.TEN, checkingAccount.getBalance());
        assertEquals(Currency.ARS, checkingAccount.getCurrency());
        assertEquals(new Long(1), checkingAccount.getId());
    }

    @Test
    public void toDTO() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setBalance(BigDecimal.TEN);
        checkingAccount.setCurrency(Currency.ARS);
        checkingAccount.setId(1L);

        CheckingAccountDTO checkingAccountDTO = CheckingAccountMapper.toDTO(checkingAccount);

        assertEquals(BigDecimal.TEN, checkingAccountDTO.getBalance());
        assertEquals(Currency.ARS, checkingAccountDTO.getCurrency());
        assertEquals(new Long(1), checkingAccountDTO.getId());
    }
}