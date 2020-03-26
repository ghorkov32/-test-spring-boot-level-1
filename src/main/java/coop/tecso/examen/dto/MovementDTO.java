package coop.tecso.examen.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class MovementDTO {
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String description;
    @NotNull
    private Date date;

    public MovementDTO() {
    }


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
