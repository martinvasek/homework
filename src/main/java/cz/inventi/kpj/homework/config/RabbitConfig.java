package cz.inventi.kpj.homework.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@EnableRabbit
@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

  public static final String APP_FANOUT_EXCHANGE = "kpj";

  private final AppProperties appProperties;

  @Bean
  Queue appQueue() {
    return new Queue(appProperties.getQueueName());
  }

  @Bean
  FanoutExchange appExchange() {
    return new FanoutExchange(APP_FANOUT_EXCHANGE);
  }

  @Bean
  Binding appBinding() {
    return BindingBuilder.bind(appQueue()).to(appExchange());
  }
}
