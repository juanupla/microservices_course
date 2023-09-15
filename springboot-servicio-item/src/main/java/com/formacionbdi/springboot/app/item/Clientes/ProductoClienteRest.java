package com.formacionbdi.springboot.app.item.Clientes;

import com.formacionbdi.springboot.app.item.Models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "servicio-productos")/*,url = "http://localhost:8001"*/ //el puerto se usaba cuando consumia un cliente http SIN EUREKA y sin Ribbon, ahora como usamos eureka es el mismo que se encarga del balanceo de carga(cuando existen 2 o más instancias). solo debemos indicarle el nombre del servicio que consume
public interface ProductoClienteRest {


    @GetMapping("/Productos/AllProductos")
    List<Producto> productos();
    @GetMapping("/Productos/ProdcutoById/{id}")
    Producto producto(@PathVariable Long id);

}
