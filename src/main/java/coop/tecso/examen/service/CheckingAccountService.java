package coop.tecso.examen.service;

import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Movement;

import java.util.List;
import java.util.NoSuchElementException;

public interface CheckingAccountService {

    CheckingAccount createCheckingAccount(CheckingAccount checkingAccount);
    CheckingAccount findById(Long id) throws NoSuchElementException;
    List<CheckingAccount> findAll();
    void addMovementToCheckingAccountById(Long id, Movement movement) throws NoSuchElementException;
    void deleteAccount(Long id) throws NoSuchElementException, IllegalStateException;
    List<Movement> getMovementsByCheckingAccountId(Long id) throws NoSuchElementException;
}
