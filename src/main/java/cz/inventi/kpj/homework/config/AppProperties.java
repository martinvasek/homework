package cz.inventi.kpj.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(value = "app")
@Configuration
public class AppProperties {

  private String externalPort;
  private String queueName;
  private String name;
}
