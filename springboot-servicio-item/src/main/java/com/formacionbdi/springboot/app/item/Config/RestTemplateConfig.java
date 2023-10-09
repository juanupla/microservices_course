package com.formacionbdi.springboot.app.item.Config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    //-----------------------------------------------------------------------------------------------------------------------
    //----------ESTE BEAN LO UTILIZAMOS PARA CONFIGURAR EL CIRCUITBREAKERFACTORY :{ ESTA CONFIGURACION LUEGO SE HIZO EN UN APPLICATION.YML
    //          Y TIENE MAYOR PRIORIDAD QUE ESTA CONFIGURACION }
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCostumazer(){
        return factory -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
                            .slidingWindowSize(10)                           //tamaño de la muestra
                            .failureRateThreshold(50)                        //taza de falla en 50%
                            .waitDurationInOpenState(Duration.ofSeconds(25L))//tiempo de espera en estado aierto
                            .permittedNumberOfCallsInHalfOpenState(5)        //numero de llamadas en el estado semi-abierto
                            .slowCallDurationThreshold(Duration.ofSeconds(2L))//llamadas lentas salen con error despues de x segundos.
                            //Pero el timeout SUCEDE SIEMPRE PRIMERO!!
                            .build())
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(7L)).build())//establecemos tiempo que puede demorar la respuesta
                    .build();
        });
    }
}
