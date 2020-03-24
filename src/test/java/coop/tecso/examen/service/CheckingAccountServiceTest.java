package coop.tecso.examen.service;

import coop.tecso.examen.enums.Currency;
import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Movement;
import coop.tecso.examen.repository.CheckingAccountRepository;
import coop.tecso.examen.repository.MovementsRepository;
import coop.tecso.examen.service.impl.CheckingAccountServiceImpl;
import org.hibernate.annotations.Check;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CheckingAccountServiceTest {

    @Mock
    CheckingAccountRepository checkingAccountRepository;

    @Mock
    MovementsRepository movementsRepository;

    @InjectMocks
    private CheckingAccountServiceImpl checkingAccountService;

    @Before
    public void setUp(){
        movementsRepository = mock(MovementsRepository.class);
        checkingAccountRepository = mock(CheckingAccountRepository.class);
        checkingAccountService = new CheckingAccountServiceImpl(checkingAccountRepository, movementsRepository);
    }

    @Test
    public void createCheckingAccount() {
        CheckingAccount checkingAccount = mock(CheckingAccount.class);

        doReturn(checkingAccount).when(checkingAccountRepository).save(checkingAccount);

        CheckingAccount checkingAccount1 = checkingAccountService.createCheckingAccount(checkingAccount);

        verify(checkingAccountRepository, times(1)).save(checkingAccount);

        assertEquals(checkingAccount, checkingAccount1);
    }

    @Test
    public void findById() {
        CheckingAccount checkingAccount = mock(CheckingAccount.class);

        doReturn(Optional.of(checkingAccount)).when(checkingAccountRepository).findById(1L);

        CheckingAccount checkingAccount1 = checkingAccountService.findById(1L);

        assertEquals(checkingAccount, checkingAccount1);
    }

    @Test(expected = NoSuchElementException.class)
    public void findByNonExistentId() {
        doReturn(Optional.empty()).when(checkingAccountRepository).findById(1L);

        checkingAccountService.findById(1L);
    }

    @Test
    public void findAll() {
        CheckingAccount checkingAccount = mock(CheckingAccount.class);

        CheckingAccount checkingAccountB = mock(CheckingAccount.class);

        CheckingAccount checkingAccountC = mock(CheckingAccount.class);

        List<CheckingAccount> list = new ArrayList<>();
        list.add(checkingAccount);
        list.add(checkingAccountB);
        list.add(checkingAccountC);
        doReturn(list).when(checkingAccountRepository).findAll();

        assertEquals(list, checkingAccountService.findAll());
    }

    @Test
    public void addMovementToCheckingAccountById() {
        Movement movement = mock(Movement.class);

        when(movement.getAmount()).thenReturn(new BigDecimal(50));

        CheckingAccount checkingAccount = spy(CheckingAccount.class);
        when(checkingAccount.getCurrency()).thenReturn(Currency.ARS);
        checkingAccount.setBalance(new BigDecimal(100));
        checkingAccount.setMovements(new ArrayList<>());

        doReturn(Optional.of(checkingAccount)).when(checkingAccountRepository).findById(1L);
        doReturn(checkingAccount).when(checkingAccountRepository).save(checkingAccount);

        checkingAccountService.addMovementToCheckingAccountById(1L, movement);

        assertEquals(new BigDecimal(150), checkingAccount.getBalance());
        assertEquals(1, checkingAccount.getMovements().size());

    }
    @Test
    public void addMovementToCheckingAccountByIdWithoutInstantiatedMovements() {
        Movement movement = mock(Movement.class);

        when(movement.getAmount()).thenReturn(new BigDecimal(50));

        CheckingAccount checkingAccount = spy(CheckingAccount.class);
        when(checkingAccount.getCurrency()).thenReturn(Currency.ARS);
        checkingAccount.setBalance(new BigDecimal(100));

        doReturn(Optional.of(checkingAccount)).when(checkingAccountRepository).findById(1L);
        doReturn(checkingAccount).when(checkingAccountRepository).save(checkingAccount);

        checkingAccountService.addMovementToCheckingAccountById(1L, movement);

        assertEquals(new BigDecimal(150), checkingAccount.getBalance());
        assertEquals(1, checkingAccount.getMovements().size());

    }

    @Test(expected = IllegalArgumentException.class)
    public void overdraftLimitOnARSWhenAddingMovementToCheckingAccount() {
        Movement movement = mock(Movement.class);

        when(movement.getAmount()).thenReturn(new BigDecimal(-1500));

        CheckingAccount checkingAccount = spy(CheckingAccount.class);
        when(checkingAccount.getCurrency()).thenReturn(Currency.ARS);
        checkingAccount.setBalance(new BigDecimal(0));
        checkingAccount.setMovements(new ArrayList<>());

        doReturn(Optional.of(checkingAccount)).when(checkingAccountRepository).findById(1L);
        doReturn(checkingAccount).when(checkingAccountRepository).save(checkingAccount);

        checkingAccountService.addMovementToCheckingAccountById(1L, movement);

        verify(checkingAccountRepository, times(1)).save(checkingAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void overdraftLimitOnUSDWhenAddingMovementToCheckingAccount() {
        Movement movement = mock(Movement.class);

        when(movement.getAmount()).thenReturn(new BigDecimal(-500));

        CheckingAccount checkingAccount = spy(CheckingAccount.class);
        when(checkingAccount.getCurrency()).thenReturn(Currency.USD);
        checkingAccount.setBalance(new BigDecimal(0));
        checkingAccount.setMovements(new ArrayList<>());

        doReturn(Optional.of(checkingAccount)).when(checkingAccountRepository).findById(1L);
        doReturn(checkingAccount).when(checkingAccountRepository).save(checkingAccount);

        checkingAccountService.addMovementToCheckingAccountById(1L, movement);

        verify(checkingAccountRepository, times(1)).save(checkingAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void overdraftLimitOnEURWhenAddingMovementToCheckingAccount() {
        Movement movement = mock(Movement.class);

        when(movement.getAmount()).thenReturn(new BigDecimal(-200));

        CheckingAccount checkingAccount = spy(CheckingAccount.class);
        when(checkingAccount.getCurrency()).thenReturn(Currency.EUR);
        checkingAccount.setBalance(new BigDecimal(0));
        checkingAccount.setMovements(new ArrayList<>());

        doReturn(Optional.of(checkingAccount)).when(checkingAccountRepository).findById(1L);
        doReturn(checkingAccount).when(checkingAccountRepository).save(checkingAccount);

        checkingAccountService.addMovementToCheckingAccountById(1L, movement);

        verify(checkingAccountRepository, times(1)).save(checkingAccount);
    }

    @Test
    public void deleteAccountWithoutMovements() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setCurrency(Currency.EUR);
        checkingAccount.setMovements(new ArrayList<>());
        checkingAccount.setBalance(BigDecimal.ZERO);

        doReturn(Optional.of(checkingAccount)).when(checkingAccountRepository).findById(1L);

        checkingAccountService.deleteAccount(1L);

        verify(checkingAccountRepository, times(1)).delete(checkingAccount);
    }

    @Test(expected = IllegalStateException.class)
    public void deleteAccountWithMovements() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setCurrency(Currency.EUR);
        checkingAccount.setMovements(new ArrayList<>());
        checkingAccount.setBalance(BigDecimal.ZERO);

        checkingAccount.getMovements().add(mock(Movement.class));

        doReturn(Optional.of(checkingAccount)).when(checkingAccountRepository).findById(1L);

        checkingAccountService.deleteAccount(1L);
    }

    @Test
    public void getMovementsByExistingCheckingAccountId() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setCurrency(Currency.EUR);
        checkingAccount.setMovements(new ArrayList<>());
        checkingAccount.setBalance(BigDecimal.ZERO);

        checkingAccount.getMovements().add(mock(Movement.class));
        checkingAccount.getMovements().add(mock(Movement.class));
        checkingAccount.getMovements().add(mock(Movement.class));

        doReturn(Optional.of(checkingAccount)).when(checkingAccountRepository).findById(1L);
        doReturn(checkingAccount.getMovements()).when(movementsRepository).findAllByCheckingAccountOrderByDateDesc(checkingAccount);

        List<Movement> movements = checkingAccountService.getMovementsByCheckingAccountId(1L);

        assertEquals(3, movements.size());
    }

}