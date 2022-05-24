package cz.inventi.kpj.homework.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import cz.inventi.kpj.homework.service.KpjServicesService;
import cz.inventi.kpj.openapi.model.ServiceDTO;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceRegistrationListener {

  private final KpjServicesService servicesService;

  @RabbitListener(queues = "${app.queue-name}")
  void register(String message) {
    String[] messageDetails = message.split(";");
    if (messageDetails.length == 2) {
      ServiceDTO newKpjService = new ServiceDTO().name(messageDetails[0]).port(messageDetails[1]).registerTime(OffsetDateTime.now());
      servicesService.saveKpjService(newKpjService);
    } else {
      log.error("Invalid message has been received: {}", message);
    }
  }
}
