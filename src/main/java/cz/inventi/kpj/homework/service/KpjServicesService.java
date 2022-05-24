package cz.inventi.kpj.homework.service;

import cz.inventi.kpj.openapi.model.ServiceDTO;

import java.util.List;
import java.util.Optional;

public interface KpjServicesService {

    List<ServiceDTO> getAllServices();

    Optional<ServiceDTO> getKpjService(String kpjServiceName);

    void register();

    void saveKpjService(ServiceDTO kpjService);
}
