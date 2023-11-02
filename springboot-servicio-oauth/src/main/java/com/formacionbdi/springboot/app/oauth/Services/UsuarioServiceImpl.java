package com.formacionbdi.springboot.app.oauth.Services;

import com.formacionbdi.springboot.app.oauth.Clients.UsuarioFeignClient;
import com.formacionbdi.springboot.app.oauth.Models.UsuarioDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UserDetailsService { //interfaz propia de spring security
    //Se encarga de autenticar al usuario.

    private Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    @Autowired
    private UsuarioFeignClient client;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioDto usuarioDto = client.findUserById(username);
        if(username == null){
            throw new ErrorResponseException(HttpStatusCode.valueOf(404));
        }
        //La interfaz GrantedAuthority se utiliza para representar un privilegio o autorización
        // en una aplicación. Cada objeto GrantedAuthority representa un rol o permiso que un usuario o entidad
        // tiene en el sistema. Estas autoridades se utilizan para controlar el acceso
        List<GrantedAuthority> authorities = usuarioDto.getRoles().stream().map((rol ->
                new SimpleGrantedAuthority(rol.getNombre())))//por cada rol instanciamos un SimpleGranted...
                .peek(simpleGrantedAuthority -> log.info("Role: "+ simpleGrantedAuthority.getAuthority()))
                .collect(Collectors.toList());
        return new User(usuarioDto.getUserName(),usuarioDto.getPassword(),usuarioDto.isEnabled(), true, true,true,authorities );
    }
}
