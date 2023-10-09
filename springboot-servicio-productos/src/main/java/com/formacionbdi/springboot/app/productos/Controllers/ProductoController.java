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
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<ProductoDTO> ProdcutoById(@PathVariable Long id) throws InterruptedException {
        if(id.equals(10L)){

            //simulamos un error. Es para probar cortocircuito en item
            throw new IllegalStateException("Producto no encontrado");
        }
        if(id.equals(7L)){

            //simulamos un timeout, tambien agregamos para esto throws InterruptedException. Es para probar cortocircuito en item
            TimeUnit.SECONDS.sleep(5L);
        }
        return ResponseEntity.ok(iProductoService.findById(id));
    }
}
