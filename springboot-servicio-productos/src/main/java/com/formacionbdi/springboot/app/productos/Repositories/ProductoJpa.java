package com.formacionbdi.springboot.app.productos.Repositories;

import com.formacionbdi.springboot.app.productos.Entities.ProductoEntity;
import com.formacionbdi.springboot.app.productos.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoJpa extends JpaRepository<ProductoEntity,Long> {
    Optional<Producto> findProductoByNombre(String nombre);
}
