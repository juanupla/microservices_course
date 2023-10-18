package com.formacionbdi.springboot.app.productos.Services;

import com.formacionbdi.springboot.app.productos.Models.DTOs.ProductoDTO;
import com.formacionbdi.springboot.app.productos.Models.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductoService {
    List<ProductoDTO> findAll();
    ProductoDTO findById(Long id);
    ProductoDTO save(ProductoDTO productoDTO);
    Producto update (Producto producto);
    Producto delete(Long id);
}
