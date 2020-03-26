package coop.tecso.examen.service.impl;

import coop.tecso.examen.enums.Currency;
import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Movement;
import coop.tecso.examen.repository.CheckingAccountRepository;
import coop.tecso.examen.repository.MovementsRepository;
import coop.tecso.examen.service.CheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CheckingAccountServiceImpl implements CheckingAccountService {

    private final CheckingAccountRepository checkingAccountRepository;

    private final MovementsRepository movementsRepository;

    @Autowired
    public CheckingAccountServiceImpl(CheckingAccountRepository checkingAccountRepository, MovementsRepository movementsRepository) {
        this.checkingAccountRepository = checkingAccountRepository;
        this.movementsRepository = movementsRepository;
    }

    /**
     * Creates a checking account from entity object
     * @param checkingAccount   checking account to create
     * @return                  persisted checking account
     */
    @Override
    public CheckingAccount createCheckingAccount(CheckingAccount checkingAccount) {
        return checkingAccountRepository.save(checkingAccount);
    }

    /**
     * Finds a checking account by id
     * @param id    id for checking account to find
     * @return      checking account found
     * @throws NoSuchElementException   when no checking account with given id is found
     */
    @Override
    public CheckingAccount findById(Long id) throws NoSuchElementException {
        return checkingAccountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró la cuenta corriente"));
    }

    /**
     * Retrieves all checking accounts
     * @return  list of checking accounts
     */
    @Override
    public List<CheckingAccount> findAll() {
        return checkingAccountRepository.findAll();
    }

    /**
     * Adds a movement to checking account, checking overdraft limit prior save
     * @param id            checking account to add movement
     * @param movement      movement to add
     * @throws NoSuchElementException       when no checking account with given id is found
     * @throws IllegalArgumentException     when overdraft is surpassed according to the limit given by the currency
     */
    @Override
    public void addMovementToCheckingAccountById(Long id, Movement movement)
            throws NoSuchElementException, IllegalArgumentException {
        CheckingAccount checkingAccount = findById(id);
        if(checkingAccount.getMovements() == null)
            checkingAccount.setMovements(new ArrayList<>());
        checkingAccount.getMovements().add(movement);
        movement.setCheckingAccount(checkingAccount);
        checkingAccount.setBalance(checkingAccount.getBalance().add(movement.getAmount()));
        if(checkingAccount.getBalance().compareTo(Currency.getLimit(checkingAccount.getCurrency())) <= 0)
            throw new IllegalArgumentException("El movimiento supera el límite de sobregiro");
        checkingAccountRepository.save(checkingAccount);
    }

    /**
     * Deletes account with given id
     * @param id    id for the checking account to remove
     * @throws NoSuchElementException   when checking account with given id is not found
     * @throws IllegalStateException    when checking account has movements
     */
    @Override
    public void deleteAccount(Long id) throws NoSuchElementException, IllegalStateException {
        CheckingAccount checkingAccount = findById(id);
        if (checkingAccount.getMovements().size() > 0)
            throw new IllegalStateException("La cuenta #" + checkingAccount.getId() + " no se puede borrar. Posee movimientos.");
        checkingAccountRepository.delete(checkingAccount);
    }

    /**
     * Gets sorted movements for the given account
     * @param id    id for the checking account to get the movements
     * @return      list of movements sorted in descending order
     */
    @Override
    public List<Movement> getMovementsByCheckingAccountId(Long id) {
        return movementsRepository.findAllByCheckingAccountOrderByDateDesc(findById(id));
    }
}
