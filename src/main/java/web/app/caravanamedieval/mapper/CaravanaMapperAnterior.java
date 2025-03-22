package web.app.caravanamedieval.mapper;

import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.model.*;

public class CaravanaMapperAnterior {

    public static CaravanaDTO toDTO(Caravana caravana){
        if(caravana == null) return null;

        CaravanaDTO caravanaDTO = new CaravanaDTO();

        caravanaDTO.setIdCaravana(caravana.getIdCaravana());
        caravanaDTO.setNombre(caravana.getNombre());
        caravanaDTO.setVelocidad(caravana.getVelocidad());
        caravanaDTO.setCapacidadMaxima(caravana.getCapacidadMaxima());
        caravanaDTO.setDineroDisponible(caravana.getDineroDisponible());
        caravanaDTO.setPuntosVida(caravana.getPuntosVida());

        return caravanaDTO;
    }

    public static Caravana toEntity(CaravanaDTO dto){
        if(dto == null) return null;

        Caravana caravana = new Caravana();

        caravana.setIdCaravana(dto.getIdCaravana());
        caravana.setNombre(dto.getNombre());
        caravana.setVelocidad(dto.getVelocidad());
        caravana.setCapacidadMaxima(dto.getCapacidadMaxima());
        caravana.setDineroDisponible(dto.getDineroDisponible());
        caravana.setPuntosVida(dto.getPuntosVida());

        return caravana;
    }
}
