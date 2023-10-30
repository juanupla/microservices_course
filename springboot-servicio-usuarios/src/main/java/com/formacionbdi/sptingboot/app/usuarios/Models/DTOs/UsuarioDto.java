package com.formacionbdi.sptingboot.app.usuarios.Models.DTOs;

import com.formacionbdi.sptingboot.app.usuarios.Entities.RolEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    @NotNull
    private String userName;
    @NotNull
    private String password;

    private boolean enabled;

    private String nombre;

    private String apellido;

    private String email;

    private List<RolEntity> roles;
}
