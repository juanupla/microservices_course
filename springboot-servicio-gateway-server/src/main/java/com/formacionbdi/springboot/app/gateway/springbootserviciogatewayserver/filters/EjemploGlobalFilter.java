package com.formacionbdi.springboot.app.gateway.springbootserviciogatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class EjemploGlobalFilter implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger(EjemploGlobalFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) { //para implementar la clase en springcloud-gateway debemos trabajar con programación reactiva con webFlux(la cuál en este curso vemos muy por arriba)
        logger.info("ejecutando filtro pre");//mensajes para consola, já
        exchange.getRequest().mutate().headers(h -> h.add("token","1234"));//codigo en post, a efectos de ejemplo definomos a "mano"


        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("ejecurando filtro post");//mensajes para consola, já

            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor -> {exchange.getResponse().getHeaders().add("token",valor);});

            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color","rojo").build());
            exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN); //la salida del controller devolverá un texto plano(y no un json) y lo seteado en cookies; btw: cookies? color=rojo? se utilizan, por ejemplo, si el usuario cambio su navegador a una version dark, luego se va y regresa mas tarde, al loggearse se verifica esta cookie y si el usuario tenia un modo "dark" en nuestra web, esta se inicializará con dicha configuración
        })); //en el then manejamos el post filter
    }
}
