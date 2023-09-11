package com.formacionbdi.springboot.app.productos.Repositories;

import com.formacionbdi.springboot.app.productos.Entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoJpa extends JpaRepository<ProductoEntity,Long> {
}
