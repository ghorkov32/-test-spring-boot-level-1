package coop.tecso.examen.enums.converter;

import coop.tecso.examen.enums.Currency;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class CurrencyTypeConverterTest {

    @Test
    public void convertToDatabaseColumn() {
        CurrencyTypeConverter converter = new CurrencyTypeConverter();
        assertEquals("ARS", converter.convertToDatabaseColumn(Currency.ARS));
    }

    @Test
    public void convertToEntityAttribute() {
        CurrencyTypeConverter converter = new CurrencyTypeConverter();
        assertEquals(Currency.ARS, converter.convertToEntityAttribute("ARS"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToUnknownEntityAttribute() {
        CurrencyTypeConverter converter = new CurrencyTypeConverter();
        Currency zzz = converter.convertToEntityAttribute("ZZZ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToNullDatabaseColumn() {
        CurrencyTypeConverter converter = new CurrencyTypeConverter();
        converter.convertToDatabaseColumn(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToNullEntityAttribute() {
        CurrencyTypeConverter converter = new CurrencyTypeConverter();
        converter.convertToEntityAttribute(null);
    }
}
