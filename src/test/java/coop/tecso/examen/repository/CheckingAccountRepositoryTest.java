package coop.tecso.examen.repository;

import coop.tecso.examen.enums.Currency;
import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CheckingAccountRepositoryTest {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Before
    public void setUp() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setCurrency(Currency.ARS);
        checkingAccount.setMovements(new ArrayList<>());
        checkingAccount.setBalance(BigDecimal.ZERO);

        checkingAccountRepository.save(checkingAccount);
    }

    @Test
    public void findAllMustReturnAllCheckingAccounts() {
        List<CheckingAccount> result = checkingAccountRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void assertNewlyCreatedCheckingAccountHasNoBalance() {
        List<CheckingAccount> result = checkingAccountRepository.findAll();
        assertEquals(BigDecimal.ZERO, result.get(0).getBalance());
    }

    @Test(expected = ConstraintViolationException.class)
    public void assertExceptionWhenNullBalance() {

        CheckingAccount checkingAccountNullBalance = new CheckingAccount();
        checkingAccountNullBalance.setCurrency(Currency.ARS);
        ConstraintViolationException ex = null;
        checkingAccountRepository.save(checkingAccountNullBalance);

    }

    @Test(expected = ConstraintViolationException.class)
    public void assertExceptionWhenNullCurrency() {

        CheckingAccount checkingAccountNullCurrency = new CheckingAccount();
        checkingAccountNullCurrency.setBalance(BigDecimal.ZERO);
        checkingAccountRepository.save(checkingAccountNullCurrency);
    }

}
