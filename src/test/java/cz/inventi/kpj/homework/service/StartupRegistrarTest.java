package cz.inventi.kpj.homework.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import cz.inventi.kpj.homework.AbstractUnitTest;

class StartupRegistrarTest extends AbstractUnitTest {

    @Mock
    private KpjServicesService kpjServicesService;

    @InjectMocks
    private StartupRegistrar tested;

    @Test
    void serviceStringIsProperlyBuiltAndSent() {
        ApplicationReadyEvent mockEvent = Mockito.mock(ApplicationReadyEvent.class);
        tested.onApplicationEvent(mockEvent);

        verify(kpjServicesService).register();
    }

}