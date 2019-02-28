package edu.xinan.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by hoaven on 2019/2/27
 */
@Configuration
public class WebClientConfig {
  @Autowired
  private LoadBalancerExchangeFilterFunction lbFunction;

  @Bean
  public WebClient webClient(){
    return WebClient.builder()
        .filter(lbFunction)
        .build();
  }
}
