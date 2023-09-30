package com.formacionbdi.springboot.app.gateway.springbootserviciogatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {
    //si no implementamos metodo para definir el nombre, por defecto sera "Ejemplo" que sustrae de EjemploGatewayFilterFactory

    public EjemploGatewayFilterFactory() {
        super(Configuracion.class);
    }

    private final Logger logger = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Configuracion config){
        return (exchange, chain) -> {
            logger.info("ejecutando pre gateway filter factory: " + config.mensaje);
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                Optional.ofNullable(config.cookieValor).ifPresent(cookie->{
                    exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre,cookie).build());
                });
                logger.info("ejecutando post gateway filter factory: " + config.mensaje);
            }));
        };
    }

    @Override
    public String name() {
        return  "EjemploCookie";
        //como vemos al principio de la clase, el metodo por defecto se llama Ejemplo(y de esta manera nos referiremos a el en el filter del yml(propertie)
        // acá solo le cambiamos el nombre por el cual deseamos hacer referecncia
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("mensaje","cookieNombre","cookieValor");
        //esto lo definimos asi para que en la configuracion de yml sea más corta, en una sola linea y separados por coma(",")
    }



    public static class Configuracion{
        private String mensaje;
        private String cookieValor;
        private String cookieNombre;
        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getCookieValor() {
            return cookieValor;
        }

        public void setCookieValor(String cookieValor) {
            this.cookieValor = cookieValor;
        }

        public String getCookieNombre() {
            return cookieNombre;
        }

        public void setCookieNombre(String cookieNombre) {
            this.cookieNombre = cookieNombre;
        }

    }
}
