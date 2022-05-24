package cz.inventi.kpj.homework.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StartupRegistrar implements ApplicationListener<ApplicationReadyEvent> {

    private final KpjServicesService kpjServicesService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        kpjServicesService.register();
    }

}
