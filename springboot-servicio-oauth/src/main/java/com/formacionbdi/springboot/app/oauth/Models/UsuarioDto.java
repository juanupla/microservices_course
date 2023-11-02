package com.formacionbdi.springboot.app.oauth.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private String userName;

    private String password;

    private boolean enabled;

    private String nombre;

    private String apellido;

    private String email;

    private List<Rol> roles;
}
