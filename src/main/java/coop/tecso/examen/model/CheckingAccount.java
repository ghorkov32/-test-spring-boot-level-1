package coop.tecso.examen.model;

import coop.tecso.examen.enums.Currency;
import coop.tecso.examen.enums.converter.CurrencyTypeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "CHECKING_ACCOUNT")
public class CheckingAccount extends AbstractPersistentObject {

    @Column(name = "CURRENCY")
    @NotNull
    @Convert(converter = CurrencyTypeConverter.class)
    private Currency currency;

    @NotNull
    @Column(name = "BALANCE", columnDefinition="Decimal(12,2) default '0.00'")
    private BigDecimal balance;

    @OneToMany(mappedBy = "checkingAccount")
    private List<Movement> movements;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }
}
