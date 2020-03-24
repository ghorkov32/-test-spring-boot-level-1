package coop.tecso.examen.dto;

import coop.tecso.examen.enums.Currency;

import java.math.BigDecimal;

public class CheckingAccountDTO {
    private Long id;
    private BigDecimal balance;
    private Currency currency;
}
