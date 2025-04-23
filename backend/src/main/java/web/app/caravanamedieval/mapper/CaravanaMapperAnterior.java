package web.app.caravanamedieval.mapper;

import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.model.*;

public class CaravanaMapperAnterior {

    public static CaravanDTO toDTO(Caravan caravan){
        if(caravan == null) return null;

        CaravanDTO caravanDTO = new CaravanDTO();

        caravanDTO.setName(caravan.getName());
        caravanDTO.setSpeed(caravan.getSpeed());
        caravanDTO.setMaxCapacity(caravan.getMaxCapacity());
        caravanDTO.setAvailableMoney(caravan.getAvailableMoney());
        caravanDTO.setLifePoints(caravan.getLifePoints());

        return caravanDTO;
    }

    public static Caravan toEntity(CaravanDTO dto){
        if(dto == null) return null;

        Caravan caravan = new Caravan();

        caravan.setName(dto.getName());
        caravan.setSpeed(dto.getSpeed());
        caravan.setMaxCapacity(dto.getMaxCapacity());
        caravan.setAvailableMoney(dto.getAvailableMoney());
        caravan.setLifePoints(dto.getLifePoints());

        return caravan;
    }
}
