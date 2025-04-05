package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ServicesDTO;
import web.app.caravanamedieval.model.Services;

@Mapper(componentModel = "spring")
public interface ServicesMapper {
    ServicesMapper INSTANCE = Mappers.getMapper(ServicesMapper.class);

    @Mapping(target = "idService", ignore = true)
    Services toEntity(ServicesDTO serviceDTO);
    ServicesDTO toDTO(Services service);

    @Mapping(target = "idService", ignore = true)
    void updateEntity(@MappingTarget Services entity, ServicesDTO dto);
}
