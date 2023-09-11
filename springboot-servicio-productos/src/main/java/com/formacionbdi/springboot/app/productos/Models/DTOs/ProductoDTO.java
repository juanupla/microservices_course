package com.formacionbdi.springboot.app.productos.Models.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    private String nombre;

    private Double precio;

    private LocalDate createAt;
}
