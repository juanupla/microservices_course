package com.formacionbdi.springboot.app.oauth.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //acá vamos a configurar el authenticationManager y otras cosas como el token storage(que tiene que ser
        // del tipo jwt) y el converter que es el que se encarga de guardar los datos del usuario en el token
        //y cualquier tipo de informacion.

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    //Spring se genera con componentes para la configuracion
    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        //esta es la clave secreta. Y deberia hacerse en el gateway, lo vamos a hacer mas adelante,
        // por ahora lo dejamos en duro
        tokenConverter.setSigningKey("codigoSecreto");
        return tokenConverter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("frontEndApp_identificadorCliente")//cliente que consumira nuestra app
                .secret(bCryptPasswordEncoder.encode("123456"))//encriptamos
                .scopes("read","write")//alcances o PERMISOS de la applicacion cliente
                .authorizedGrantTypes("password","refresh_token")//es como vamos a auntenticar a nuestros usuarios y como tenemos user y passwor indicamos "password"
                // y Refresh es para refrescar el token justo antes que caduque
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600);
                //esta cadena podria continuar con un .withClient() y toda la secuencia para registrar otro cliente..
    }

    //permisos que van a tener nuestros endpoints del servidor de oauth2, para generar y/o validar el token
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")//cualquiera puede acceder a esta ruta para crear el token
                .checkTokenAccess("isAuthenticated()");//esta ruta es para validar el token requiere autenticacion
        //estos dos endpoints estan protegidos por Header Authorization Basic (que es la autorizacion de clientes-como frondEndApp-
        // y no de usuarios que viajan a travez del Bearer)
    }
}
