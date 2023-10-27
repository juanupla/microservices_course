package com.formacionbdi.springboot.app.item.Services;

import com.formacionbdi.springboot.app.item.Models.DTOs.ProductoDTO;
import com.formacionbdi.springboot.app.item.Models.Item;
import com.formacionbdi.springboot.app.item.Models.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IItemService {
    List<Item> finAll();
    Item finById(Long id, Integer cantidad);

    ProductoDTO save(ProductoDTO productoDTO);
    Producto update (Producto producto);
    Producto delete(Long id);
}
