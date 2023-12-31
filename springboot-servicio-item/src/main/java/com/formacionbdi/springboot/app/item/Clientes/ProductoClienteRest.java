package com.formacionbdi.springboot.app.item.Clientes;

import com.formacionbdi.springboot.app.item.Models.DTOs.ProductoDTO;
import com.formacionbdi.springboot.app.item.Models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "servicio-productos")/*,url = "http://localhost:8001"*/ //el puerto se usaba cuando consumia un cliente http SIN EUREKA y sin Ribbon, ahora como usamos eureka es el mismo que se encarga del balanceo de carga(cuando existen 2 o más instancias). solo debemos indicarle el nombre del servicio que consume
public interface ProductoClienteRest {


    @GetMapping("/AllProductos")
    List<Producto> productos();
    @GetMapping("/ProdcutoById/{id}")
    Producto producto(@PathVariable Long id);
    @PostMapping("/Crear")
    ProductoDTO create(@RequestBody ProductoDTO productoDTO);
    @PutMapping("/Actualizar")
    Producto update(@RequestBody Producto producto);
    @DeleteMapping("/Eliminar/{id}")
    Producto delete(@PathVariable Long id);

}
