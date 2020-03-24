package coop.tecso.examen.dto;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.time.DateTimeException;

public class MovementDTO {
    private Long id;
    private BigDecimal amount;
    private String description;
    private DateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
