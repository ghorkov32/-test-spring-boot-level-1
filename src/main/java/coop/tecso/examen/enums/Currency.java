package coop.tecso.examen.enums;

public enum Currency {
    USD,
    EUR,
    ARS,
    UNKNOWN;

    public static Currency getEnum(String value) {
        for(Currency v : values())
            if(v.toString().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
