package cz.inventi.kpj.homework.service;

import static cz.inventi.kpj.homework.config.RabbitConfig.APP_FANOUT_EXCHANGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import cz.inventi.kpj.homework.AbstractIT;
import cz.inventi.kpj.homework.config.AppProperties;
import cz.inventi.kpj.homework.repository.KpjServiceEntityRepository;
import cz.inventi.kpj.openapi.model.ServiceDTO;

@Slf4j
class KpjServicesServiceIntegrationTest extends AbstractIT {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private KpjServiceEntityRepository repository;

    @SpyBean
    private AmqpTemplate amqpTemplate;

    @Autowired
    private KpjServicesServiceImpl tested;

    @BeforeEach
    void initOrCleanup() {
        repository.deleteAll();
    }

    @Test
    void onRegisterMessageIsSentWithCorrectRegistrationString() {

        tested.register();

        ArgumentCaptor<String> serviceStringCaptor = ArgumentCaptor.forClass(String.class);
        verify(amqpTemplate, atLeast(1)).convertAndSend(eq(APP_FANOUT_EXCHANGE), eq(""), serviceStringCaptor.capture());

        assertThat(serviceStringCaptor.getValue(),
                is("%s;%s".formatted(appProperties.getName(), appProperties.getExternalPort())));
    }

    @Test
    void onRegisterServiceIsSelfRegistered() throws InterruptedException {
        tested.register();

        TimeUnit.SECONDS.sleep(2L);

        List<ServiceDTO> services = tested.getAllServices();
        assertThat(services.size(), is(1));
        ServiceDTO currentService = services.get(0);
        assertThat(currentService.getName(), is(appProperties.getName()));
        assertThat(currentService.getPort(), is(appProperties.getExternalPort()));
    }

    @Test
    void afterTwoTimesSelfRegistrationsIsOneDBRecord() throws InterruptedException {

        tested.register();
        tested.register();

        TimeUnit.SECONDS.sleep(2L);

        assertThat(tested.getAllServices().size(), is(1));
    }
}