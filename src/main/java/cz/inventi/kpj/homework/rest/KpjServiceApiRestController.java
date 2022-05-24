package cz.inventi.kpj.homework.rest;

import java.util.List;
import java.util.Optional;

import cz.inventi.kpj.homework.config.AppProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.inventi.kpj.homework.service.KpjServicesService;
import cz.inventi.kpj.openapi.api.ServicesApi;
import cz.inventi.kpj.openapi.model.ServiceDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class KpjServiceApiRestController implements ServicesApi {

    private final KpjServicesService kpjServicesService;
    private final AppProperties appProperties;

    @Override
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        return ResponseEntity.ok(kpjServicesService.getAllServices());
    }

    @Override
    public ResponseEntity<ServiceDTO> getCurrent() {
        return getService(appProperties.getName());
    }

    @Override
    public ResponseEntity<ServiceDTO> getService(String name) {
        Optional<ServiceDTO> serviceOpt = kpjServicesService.getKpjService(name);
        return serviceOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> register() {
        kpjServicesService.register();
        return ResponseEntity.noContent().build();
    }
}
