package com.formacionbdi.sptingboot.app.usuarios.Services;

import com.formacionbdi.sptingboot.app.usuarios.Models.DTOs.UsuarioDto;
import com.formacionbdi.sptingboot.app.usuarios.Models.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface iUsuario {
    UsuarioDto findByUsername(String username);
    UsuarioDto newUser(UsuarioDto usuarioDto);
    UsuarioDto updateUser(UsuarioDto usuarioDto);
    UsuarioDto delete(String username);
}
