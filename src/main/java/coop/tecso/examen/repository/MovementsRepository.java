package coop.tecso.examen.repository;

import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementsRepository extends JpaRepository<Movement, Long> {
    List<Movement> findAllByCheckingAccountOrderByDateDesc(CheckingAccount checkingAccount);
}
