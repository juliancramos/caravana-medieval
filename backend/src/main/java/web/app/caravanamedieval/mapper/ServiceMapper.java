package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ServiceDTO;
import web.app.caravanamedieval.model.Service;

@Mapper
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    ServiceDTO toDTO(Service service);

    @Mapping(target = "idService", ignore = true)
    Service toEntity(ServiceDTO dto);
}

