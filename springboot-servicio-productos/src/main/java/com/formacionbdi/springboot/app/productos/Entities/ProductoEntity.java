package com.formacionbdi.springboot.app.productos.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEntity { //esta clase podria implementar(opcionalmente) Serializable, es una interfaz que permite convertir este objeto en bytes(para guardarlo en archivos de textos y demas).. suele ser una buena práctica

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column
    private String nombre;
    @Column
    private Double precio;
    @Column(name = "create_at")
    //@Temporal(TemporalType.DATE)
    private LocalDate createAt;

}
