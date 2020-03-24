package coop.tecso.examen.enums;

import java.math.BigDecimal;

/**
 * Enum for currency.
 *
 * Has a method to get the overdraft limit for each, and a conversor method to get
 * the right currency for the given string.
 */
public enum Currency {
    USD,
    EUR,
    ARS;

    public static Currency getEnum(String value) {
        for(Currency v : values())
            if(v.toString().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException("Unknown currency");
    }

    public static BigDecimal getLimit(Currency currency){
        switch (currency){
            case ARS:return new BigDecimal(-1000);
            case USD:return new BigDecimal(-300);
            case EUR:return new BigDecimal(-150);
        }
        return BigDecimal.ZERO;
    }
}
