package cz.inventi.kpj.homework.service;

import static cz.inventi.kpj.homework.config.RabbitConfig.APP_FANOUT_EXCHANGE;

import cz.inventi.kpj.homework.config.AppProperties;
import cz.inventi.kpj.homework.entity.KpjServiceEntity;
import cz.inventi.kpj.homework.mapper.KpjServiceMapper;
import cz.inventi.kpj.homework.repository.KpjServiceEntityRepository;
import cz.inventi.kpj.openapi.model.ServiceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class KpjServicesServiceImpl implements KpjServicesService {

    private final KpjServiceEntityRepository kpjServiceEntityRepository;
    private final AmqpTemplate amqpTemplate;
    private final AppProperties appProperties;

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
        log.debug("Going to self register {}", appProperties);
        String registrationString = getRegistrationString();
        amqpTemplate.convertAndSend(APP_FANOUT_EXCHANGE, "", registrationString);
    }

    @Override
    public void saveKpjService(ServiceDTO kpjService) {
        log.debug("Received service {} to be registered", kpjService);
        Optional<KpjServiceEntity> serviceEntity = kpjServiceEntityRepository.findByName(kpjService.getName());
        if (serviceEntity.isPresent()) {
            log.debug("Received service {} already registered, nothing to do", kpjService);
        } else {
            log.debug("Received new service {} sending self registration message", kpjService);
            kpjServiceEntityRepository.save(KpjServiceMapper.INSTANCE.entityFromDTO(kpjService));
            register();
        }
    }

    private String getRegistrationString() {
        return "%s;%s".formatted(appProperties.getName(), appProperties.getExternalPort());
    }
}
