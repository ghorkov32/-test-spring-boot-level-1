package coop.tecso.examen.repository;

import coop.tecso.examen.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementsRepository extends JpaRepository<Movement, Long> {

}
