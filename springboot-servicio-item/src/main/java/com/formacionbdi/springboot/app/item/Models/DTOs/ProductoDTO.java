package com.formacionbdi.springboot.app.item.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    private String nombre;

    private Double precio;

    private LocalDate createAt;
}
