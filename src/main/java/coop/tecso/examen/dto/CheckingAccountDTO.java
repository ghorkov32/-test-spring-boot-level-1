package coop.tecso.examen.dto;

import coop.tecso.examen.enums.Currency;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CheckingAccountDTO {
    private Long id;
    @NotNull
    private BigDecimal balance;
    @NotNull
    private Currency currency;

    public CheckingAccountDTO() {
    }

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
