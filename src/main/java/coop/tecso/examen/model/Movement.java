package coop.tecso.examen.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency;
import org.joda.money.Money;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Movement entity
 *
 * Has date, description, amount, and the checking account id which this movement belongs
 */
@Entity
@Table(name = "MOVEMENTS")
public class Movement extends AbstractPersistentObject {

    @Column(name = "MOVEMENT_DATE")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime",
            parameters = { @Parameter(name = "javaZone", value = "jvm")})
    @NotNull
    private DateTime date;

    @Column(name="MOVEMENT_DESCRIPTION")
    @NotNull
    private String description;

    @Column(name = "AMOUNT", columnDefinition="Decimal(12,2)")
    @NotNull
    private BigDecimal amount;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "CHECKING_ACCOUNT_ID", nullable = false)
    @NotNull
    private CheckingAccount checkingAccount;

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }
}
