package coop.tecso.examen.mappers;

import coop.tecso.examen.dto.MovementDTO;
import coop.tecso.examen.model.Movement;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class MovementMapperTest {

    @Test
    public void toEntity() {
        DateTime dateTime = new DateTime();
        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setAmount(BigDecimal.TEN);
        movementDTO.setDate(dateTime.toDate());
        movementDTO.setDescription("ZZZ");
        movementDTO.setId(1L);

        Movement movement = MovementMapper.toEntity(movementDTO);

        assertEquals(BigDecimal.TEN, movement.getAmount());
        assertEquals("ZZZ", movement.getDescription());
        assertEquals(new Long(1), movement.getId());
        assertEquals(dateTime, movement.getDate());
    }

    @Test
    public void toDTO() {
        DateTime dateTime = new DateTime();
        Movement movement = new Movement();
        movement.setAmount(BigDecimal.TEN);
        movement.setDate(dateTime);
        movement.setDescription("ZZZ");
        movement.setId(1L);

        MovementDTO movementDTO = MovementMapper.toDTO(movement);

        assertEquals(BigDecimal.TEN, movementDTO.getAmount());
        assertEquals("ZZZ", movementDTO.getDescription());
        assertEquals(new Long(1), movementDTO.getId());
        assertEquals(dateTime.toDate(), movementDTO.getDate());
    }
}