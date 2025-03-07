package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.CaravanaDTO;
import web.app.caravanamedieval.model.Caravana;

@Mapper
public interface CaravanaMapper {
    CaravanaMapper INSTANCE = Mappers.getMapper(CaravanaMapper.class);

    CaravanaDTO toDTO(Caravana caravana);

    //Ignora el id
    @Mapping(target = "idCaravana", ignore = true)
    Caravana toEntity(CaravanaDTO dto);
}
