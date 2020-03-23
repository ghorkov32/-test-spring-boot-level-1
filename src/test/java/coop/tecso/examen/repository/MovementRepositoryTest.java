package coop.tecso.examen.repository;

import coop.tecso.examen.enums.Currency;
import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Movement;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MovementRepositoryTest {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private MovementsRepository movementsRepository;


    @Test
    public void createMovementForCheckingAccountByRelationship(){

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setCurrency(Currency.ARS);
        checkingAccount.setMovements(new ArrayList<>());
        checkingAccount.setBalance(BigDecimal.ZERO);

        Movement movement = new Movement();
        movement.setAmount(BigDecimal.TEN);
        movement.setDate(new DateTime());
        movement.setDescription("ZZZ");


        checkingAccount.getMovements().add(movement);

        CheckingAccount persisted = checkingAccountRepository.save(checkingAccount);

        assertEquals(1, persisted.getMovements().size());
        assertEquals(movement, persisted.getMovements().get(0));

    }

    @Test(expected = ConstraintViolationException.class)
    public void createMovementWithoutCheckingAccount(){

        Movement movement = new Movement();
        movement.setAmount(BigDecimal.TEN);
        movement.setDate(new DateTime());
        movement.setDescription("ZZZ");

        movementsRepository.save(movement);
    }

}
