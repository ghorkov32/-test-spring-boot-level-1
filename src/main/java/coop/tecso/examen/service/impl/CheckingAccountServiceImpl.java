package coop.tecso.examen.service.impl;

import coop.tecso.examen.enums.Currency;
import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Movement;
import coop.tecso.examen.repository.CheckingAccountRepository;
import coop.tecso.examen.repository.MovementsRepository;
import coop.tecso.examen.service.CheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Override
    public CheckingAccount createCheckingAccount(CheckingAccount checkingAccount) {
        return checkingAccountRepository.save(checkingAccount);
    }

    @Override
    public CheckingAccount findById(Long id) throws NoSuchElementException {
        return checkingAccountRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<CheckingAccount> findAll() {
        return checkingAccountRepository.findAll();
    }

    @Override
    public void addMovementToCheckingAccountById(Long id, Movement movement)
            throws NoSuchElementException, IllegalArgumentException {
        CheckingAccount checkingAccount = findById(id);
        if(checkingAccount.getMovements() == null)
            checkingAccount.setMovements(new ArrayList<>());
        checkingAccount.getMovements().add(movement);
        checkingAccount.setBalance(checkingAccount.getBalance().add(movement.getAmount()));
        if(checkingAccount.getBalance().compareTo(Currency.getLimit(checkingAccount.getCurrency())) <= 0)
            throw new IllegalArgumentException("Movement amount surpasses overdraft limit, balance: " + checkingAccount.getBalance().toString());
        checkingAccountRepository.save(checkingAccount);
    }

    @Override
    public void deleteAccount(Long id) throws NoSuchElementException, IllegalStateException {
        CheckingAccount checkingAccount = findById(id);
        if (checkingAccount.getMovements().size() > 0)
            throw new IllegalStateException("Cannot delete accounts w/movements");
        checkingAccountRepository.delete(checkingAccount);
    }

    @Override
    public List<Movement> getMovementsByCheckingAccountId(Long id) {
        return movementsRepository.findAllByCheckingAccountOrderByDateDesc(findById(id));
    }
}
