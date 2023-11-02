package com.formacionbdi.springboot.app.oauth.Clients;

import com.formacionbdi.springboot.app.oauth.Models.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "servicio-usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/findByUserName/{username}")
    UsuarioDto findUserById(@PathVariable String username);
    @PostMapping("/newUser")
    UsuarioDto create(@RequestBody UsuarioDto usuarioDto);
    @PutMapping("/updateUser")
    UsuarioDto udpate(@RequestBody UsuarioDto usuarioDto);

    @DeleteMapping("/deleteUser/{username}")
    UsuarioDto delete(@PathVariable String username);

    @GetMapping("usuarios/search/buscar-username")
    public UsuarioDto findByUsername(@RequestParam String username);

}
