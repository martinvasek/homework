package cz.inventi.kpj.homework.mapper;

import cz.inventi.kpj.homework.entity.KpjServiceEntity;
import cz.inventi.kpj.openapi.model.ServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KpjServiceMapper {

    KpjServiceMapper INSTANCE = Mappers.getMapper(KpjServiceMapper.class);

    KpjServiceEntity entityFromDTO(ServiceDTO serviceDTO);

    ServiceDTO dtoFromEntity(KpjServiceEntity kpjServiceEntity);
}
