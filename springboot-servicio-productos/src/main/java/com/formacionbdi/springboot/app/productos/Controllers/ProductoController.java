package com.formacionbdi.springboot.app.productos.Controllers;

import com.formacionbdi.springboot.app.productos.Models.DTOs.ProductoDTO;
import com.formacionbdi.springboot.app.productos.Services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/Productos")
public class ProductoController {
    @Autowired
    private IProductoService iProductoService;

    @GetMapping("/AllProductos")
    public ResponseEntity<List<ProductoDTO>> AllProductos(){
        return ResponseEntity.ok(iProductoService.findAll());
    }
    @GetMapping("/ProdcutoById/{id}")
    public ResponseEntity<ProductoDTO> ProdcutoById(@PathVariable Long id){
        return ResponseEntity.ok(iProductoService.findById(id));
    }
}
