package coop.tecso.examen.mappers;

import coop.tecso.examen.dto.MovementDTO;
import coop.tecso.examen.model.Movement;
import org.joda.time.DateTime;

public class MovementMapper {

    public static Movement toEntity(MovementDTO dto){
        Movement movement = new Movement();
        movement.setDescription(dto.getDescription());
        movement.setAmount(dto.getAmount());
        movement.setDate(new DateTime(dto.getDate()));
        movement.setId(dto.getId());
        return movement;
    }

    public static MovementDTO toDTO(Movement movement){
        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setAmount(movement.getAmount());
        movementDTO.setDate(movement.getDate().toDate());
        movementDTO.setDescription(movement.getDescription());
        movementDTO.setId(movement.getId());
        return movementDTO;
    }
}
