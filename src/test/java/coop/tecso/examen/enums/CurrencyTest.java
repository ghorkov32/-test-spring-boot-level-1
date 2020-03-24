package coop.tecso.examen.enums;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CurrencyTest {

    @Test
    public void getValidEnum() {
        assertEquals(Currency.ARS, Currency.getEnum("ARS"));
        assertEquals(Currency.EUR, Currency.getEnum("EUR"));
        assertEquals(Currency.USD, Currency.getEnum("USD"));
    }

    @Test
    public void getValidLimit() {
        assertEquals(new BigDecimal(-1000), Currency.getLimit(Currency.ARS));
        assertEquals(new BigDecimal(-150), Currency.getLimit(Currency.EUR));
        assertEquals(new BigDecimal(-300), Currency.getLimit(Currency.USD));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInvalidEnum() {
        Currency.getEnum("ZZZ");
    }
}