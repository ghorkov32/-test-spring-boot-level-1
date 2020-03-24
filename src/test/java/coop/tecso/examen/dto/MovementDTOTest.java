package coop.tecso.examen.dto;

import coop.tecso.examen.mappers.MovementMapper;
import coop.tecso.examen.model.Movement;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class MovementDTOTest {

    @Test
    public void createDTO() {
        DateTime dateTime = new DateTime();
        MovementDTO movement = new MovementDTO();
        movement.setAmount(BigDecimal.TEN);
        movement.setDate(dateTime);
        movement.setDescription("ZZZ");
        movement.setId(1L);

        assertEquals(BigDecimal.TEN, movement.getAmount());
        assertEquals("ZZZ", movement.getDescription());
        assertEquals(new Long(1), movement.getId());
        assertEquals(dateTime, movement.getDate());
    }
}