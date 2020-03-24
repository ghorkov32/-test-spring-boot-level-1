package coop.tecso.examen.enums;

import java.math.BigDecimal;

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
            default: return BigDecimal.ZERO;
        }
    }
}
