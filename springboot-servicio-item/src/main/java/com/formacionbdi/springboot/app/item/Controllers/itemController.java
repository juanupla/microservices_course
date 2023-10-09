package com.formacionbdi.springboot.app.item.Controllers;

import com.formacionbdi.springboot.app.item.Models.Item;
import com.formacionbdi.springboot.app.item.Models.Producto;
import com.formacionbdi.springboot.app.item.Services.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/Items")
public class itemController {
    @Autowired
    @Qualifier("itemServiceFeign")//
    private IItemService iItemService;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory; //Esta es un forma de varias para implementar el patron


    //-----------------------------------------------------------------------------------------------------------------------
    //                  CONTIENE NOTACIONES DE PARAMETROS Y HEADERS: estos son requeridos en el servicio gateway, quien etrablece filtros
    //                                                               de lo que debe contener el request. ver application.yml!
    @GetMapping("/AllItems")
    public ResponseEntity<List<Item>> AllItems(@RequestParam(name="nombre",required = false) String nombre,@RequestHeader(name = "token-request",required = false) String token){
        //@RequestParam y @RequestHeader es para manejar las cabeceras establecidas en el filtro del microservico Gateway en el yml.
        // los name es como llamamos a estos request en los filtros y el required = false indicamos que no es obligatorio
        // ya que si no los ponemos y enviamos una petición sin estos request lanzaría una exepción

        System.out.println(nombre);
        System.out.println(token);    //a los request solo los mostraremos en consola
        return ResponseEntity.ok(iItemService.finAll());
    }
    //-----------------------------------------------------------------------------------------------------------------------
    //                  CONTIENE EJEMPLO DE CIRCUITBREAKER FACTORY: en el cual podemos usar los parametros por defecto o configurarlos nosotros (Bean en la clase RestTemplateConfig )
    @GetMapping("/ItemById/{id}/Cantidad/{cantidad}")
    public ResponseEntity<Item> itemById(@PathVariable Long id,@PathVariable Integer cantidad){
        return circuitBreakerFactory.create("items")//Este es el nombre del cortocircuito y luego el .run() donde usamos una expresion lambda
                .run(()->ResponseEntity.ok(iItemService.finById(id,cantidad)), e -> metodoAlternativo(id,cantidad)); //en caso de error "e" llamara al metodo alternativo; de no tenerlo, cada vez que abra el cortocircuito o se envie una solicitud insatisfactoria devolvera un error "NoFallBackAvailableException"
    }

    public ResponseEntity<Item> metodoAlternativo(Long id, Integer cantidad){
        Item item = new Item();
        Producto producto = new Producto();
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return ResponseEntity.ok(item);
    }
}
