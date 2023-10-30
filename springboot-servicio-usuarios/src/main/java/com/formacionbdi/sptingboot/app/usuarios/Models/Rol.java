package com.formacionbdi.sptingboot.app.usuarios.Models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {

    private Long id;
    @NotNull
    private String nombre;
}
