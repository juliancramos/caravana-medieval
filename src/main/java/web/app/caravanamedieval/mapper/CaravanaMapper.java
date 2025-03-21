package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.CaravanaDTO;
import web.app.caravanamedieval.model.Caravana;

@Mapper(componentModel = "spring")
public interface CaravanaMapper {
    CaravanaMapper INSTANCE = Mappers.getMapper(CaravanaMapper.class);

    // DTO a entidad
    Caravana toEntity(CaravanaDTO caravanaDTO);

    // Entidad a DTO
    CaravanaDTO toDTO(Caravana caravana);

    // Actualizar entidad desde DTO
    @Mapping(target = "idCaravana", ignore = true)
    @Mapping(target = "productos", ignore = true)
    @Mapping(target = "partidas", ignore = true)
    void updateEntity(@MappingTarget Caravana entity, CaravanaDTO dto);
}
