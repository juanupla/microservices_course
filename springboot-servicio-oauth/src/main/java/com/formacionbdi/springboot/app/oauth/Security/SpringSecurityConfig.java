package com.formacionbdi.springboot.app.oauth.Security;//package com.formacionbdi.springboot.app.oauth.Security;

import com.formacionbdi.springboot.app.oauth.Clients.UsuarioFeignClient;
import com.formacionbdi.springboot.app.oauth.Models.UsuarioDto;
import org.apache.catalina.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SpringSecurityConfig {
    @Autowired
    private UsuarioFeignClient userClient;
    private Logger logger= LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    UserDetailsService userDetailsService () {

        return username ->{

            UsuarioDto usuario= userClient.findByUsername(username);

            if(usuario ==null) {
                throw new UsernameNotFoundException("El usuario "+username+" no se encuentra");
            }

            List<GrantedAuthority> authorities= usuario.getRoles()
                    .stream()
                    .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                    .peek(authority ->logger.info("rol: "+ authority.getAuthority()))
                    .collect(Collectors.toList());

            logger.info("username: "+ username+"----------------------------------");
            return new User(usuario.getUserName(), usuario.getPassword(),
                    usuario.isEnabled(), true, true, true,authorities);
        };

    }
    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }
}