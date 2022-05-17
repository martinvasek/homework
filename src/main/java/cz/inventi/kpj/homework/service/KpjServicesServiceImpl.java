package cz.inventi.kpj.homework.service;

import cz.inventi.kpj.homework.entity.KpjServiceEntity;
import cz.inventi.kpj.homework.mapper.KpjServiceMapper;
import cz.inventi.kpj.homework.repository.KpjServiceEntityRepository;
import cz.inventi.kpj.openapi.model.ServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KpjServicesServiceImpl implements KpjServicesService {

    private final KpjServiceEntityRepository kpjServiceEntityRepository;

    @Override
    public List<ServiceDTO> getAllServices() {
        return kpjServiceEntityRepository.findAll()
                .stream()
                .map(kpjServiceEntity -> KpjServiceMapper.INSTANCE.dtoFromEntity(kpjServiceEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceDTO> getKpjService(String kpjServiceName) {
        Optional<KpjServiceEntity> serviceEntity = kpjServiceEntityRepository.findByName(kpjServiceName);
        return serviceEntity.isPresent() ? Optional.of(KpjServiceMapper.INSTANCE.dtoFromEntity(serviceEntity.get())) : Optional.empty();
    }

    @Override
    public void register() {

    }

    @Override
    public Long saveKpjService(ServiceDTO kpjService) {
        KpjServiceEntity newKpjService = kpjServiceEntityRepository.save(KpjServiceMapper.INSTANCE.entityFromDTO(kpjService));
        return newKpjService.getId();
    }
}
