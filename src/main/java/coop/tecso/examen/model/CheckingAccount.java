package coop.tecso.examen.model;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency;
import org.joda.money.Money;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@TypeDef(name = "MoneyAmountWithCurrencyType", typeClass = PersistentMoneyAmountAndCurrency.class)
public class CheckingAccount extends AbstractPersistentObject {
    @Columns(columns = { @Column(name = "BALANCE_CURRENCY"), @Column(name = "BALANCE_AMOUNT") })
    @Type(type="moneyAmountWithCurrencyType")
    private Money balance;
}
