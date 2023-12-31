package com.formacionbdi.springboot.app.productos.Models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    private Long  id;
    @NotNull
    private String nombre;
    @NotNull
    private Double precio;
    private LocalDate createAt;
}
