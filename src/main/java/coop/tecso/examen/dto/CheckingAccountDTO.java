package coop.tecso.examen.dto;

import coop.tecso.examen.enums.Currency;

import java.math.BigDecimal;

public class CheckingAccountDTO {
    private Long id;
    private BigDecimal balance;
    private Currency currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
