package com.formacionbdi.sptingboot.app.usuarios.Controllers;

import com.formacionbdi.sptingboot.app.usuarios.Models.DTOs.UsuarioDto;
import com.formacionbdi.sptingboot.app.usuarios.Services.iUsuario;
import jakarta.ws.rs.PATCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    @Autowired
    private iUsuario iUsuario;

    @GetMapping("/findByUserName/{username}")
    public ResponseEntity<UsuarioDto> findUserById(@PathVariable String username){
        return ResponseEntity.ok(iUsuario.findByUsername(username));
    }
    @PostMapping("/newUser")
    public ResponseEntity<UsuarioDto> create(@RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(iUsuario.newUser(usuarioDto));
    }
    @PutMapping("/updateUser")
    public ResponseEntity<UsuarioDto> udpate(@RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(iUsuario.updateUser(usuarioDto));
    }

    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<UsuarioDto> delete(@PathVariable String username){
        return ResponseEntity.ok(iUsuario.delete(username));
    }
}
