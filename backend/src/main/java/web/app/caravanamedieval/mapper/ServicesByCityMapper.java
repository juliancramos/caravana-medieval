package web.app.caravanamedieval.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ServicesByCityDTO;
import web.app.caravanamedieval.model.ServicesByCity;

@Mapper(componentModel = "spring")
public interface ServicesByCityMapper {
    ServicesByCityMapper INSTANCE = Mappers.getMapper(ServicesByCityMapper.class);

    @Mapping(source = "city.idCity", target = "cityId")
    @Mapping(source = "service.idService", target = "serviceId")
    ServicesByCityDTO toDTO(ServicesByCity entity);

    @Mapping(target = "id.cityId", source = "cityId")
    @Mapping(target = "id.serviceId", source = "serviceId")
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "service", ignore = true)
    ServicesByCity toEntity(ServicesByCityDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "service", ignore = true)
    void updateEntity(@MappingTarget ServicesByCity entity, ServicesByCityDTO dto);
}
