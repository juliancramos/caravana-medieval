package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ServicesByCaravanDTO;
import web.app.caravanamedieval.model.ServicesByCaravan;

@Mapper(componentModel = "spring")
public interface ServicesByCaravanMapper {
    ServicesByCaravanMapper INSTANCE = Mappers.getMapper(ServicesByCaravanMapper.class);

    @Mapping(source = "caravan.idCaravan", target = "caravanId")
    @Mapping(source = "services.idService", target = "serviceId")
    ServicesByCaravanDTO toDTO(ServicesByCaravan entity);

    @Mapping(target = "id.caravanId", source = "caravanId")
    @Mapping(target = "id.serviceId", source = "serviceId")
    @Mapping(target = "caravan", ignore = true)
    @Mapping(target = "services", ignore = true)
    ServicesByCaravan toEntity(ServicesByCaravanDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caravan", ignore = true)
    @Mapping(target = "services", ignore = true)
    void updateEntity(@MappingTarget ServicesByCaravan entity, ServicesByCaravanDTO dto);
}

