package coop.tecso.examen.controllers;

import coop.tecso.examen.dto.CheckingAccountDTO;
import coop.tecso.examen.dto.MovementDTO;
import coop.tecso.examen.mappers.CheckingAccountMapper;
import coop.tecso.examen.mappers.MovementMapper;
import coop.tecso.examen.service.CheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://sb-tecso-examen-front.herokuapp.com"}, maxAge = 3600)
@RestController
@RequestMapping("/accounts")
public class CheckingAccountController {

    private final CheckingAccountService checkingAccountService;

    @Autowired
    public CheckingAccountController(CheckingAccountService checkingAccountService) {
        this.checkingAccountService = checkingAccountService;
    }

    @GetMapping("{id}")
    public ResponseEntity<CheckingAccountDTO> getCheckingAccountById(@PathVariable Long id)
            throws NoSuchElementException {
        return new ResponseEntity<>(CheckingAccountMapper.toDTO(checkingAccountService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<List<CheckingAccountDTO>> getAllCheckingAccounts() {
        return new ResponseEntity<>(checkingAccountService.findAll().stream()
                .map(CheckingAccountMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity addCheckingAccount(@Valid @RequestBody CheckingAccountDTO checkingAccountDTO) {
        checkingAccountService.createCheckingAccount(CheckingAccountMapper.toEntity(checkingAccountDTO));
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCheckingAccountById(@PathVariable Long id) throws NoSuchElementException, IllegalStateException {
        checkingAccountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}/movements")
    public ResponseEntity<List<MovementDTO>> getMovementsByAccountId(@PathVariable Long id) throws NoSuchElementException {
        return new ResponseEntity<>(checkingAccountService.getMovementsByCheckingAccountId(id).stream()
                .map(MovementMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("{id}/movement")
    public ResponseEntity addMovementToCheckingAccount(@PathVariable Long id, @Valid @RequestBody MovementDTO movementDTO) throws NoSuchElementException, IllegalArgumentException {
        checkingAccountService.addMovementToCheckingAccountById(id, MovementMapper.toEntity(movementDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
