package cz.inventi.kpj.homework.rest;

import cz.inventi.kpj.homework.service.KpjServicesService;
import cz.inventi.kpj.openapi.api.ServicesApi;
import cz.inventi.kpj.openapi.model.ServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class KpjServiceApiRestController implements ServicesApi {

    private final KpjServicesService kpjServicesService;

    @Override
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        return ResponseEntity.ok(kpjServicesService.getAllServices());
    }

    @Override
    public ResponseEntity<ServiceDTO> getCurrent() {
        return ServicesApi.super.getCurrent();
    }

    @Override
    public ResponseEntity<ServiceDTO> getService(String name) {
        Optional<ServiceDTO> serviceOpt = kpjServicesService.getKpjService(name);
        ServiceDTO service = serviceOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "KPJ Service with name " + name + " not found"));
        return ResponseEntity.ok(service);
    }

    @Override
    public ResponseEntity<Void> register() {
        return ServicesApi.super.register();
    }

    /* for testing only*/
    @RequestMapping(path = "/services/new", method = RequestMethod.POST)
    public ResponseEntity<Void> createKpjService(@RequestBody ServiceDTO service) {
        Long id = kpjServicesService.saveKpjService(service);
        URI location =
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/{id}")
                        .buildAndExpand(id)
                        .toUri();
        return ResponseEntity.noContent().location(location).build();
    }
}
