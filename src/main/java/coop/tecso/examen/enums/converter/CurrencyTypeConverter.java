package coop.tecso.examen.enums.converter;

import coop.tecso.examen.enums.Currency;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CurrencyTypeConverter
        implements AttributeConverter<Currency, String> {

    public String convertToDatabaseColumn(Currency value) {
        if ( value == null ) {
            throw new IllegalArgumentException();
        }
        return value.toString();
    }

    public Currency convertToEntityAttribute(String value) {
        if ( value == null ) {
            throw new IllegalArgumentException();
        }
        return Currency.getEnum(value);

    }
}