package com.formacionbdi.springboot.app.item.Controllers;

import com.formacionbdi.springboot.app.item.Models.Item;
import com.formacionbdi.springboot.app.item.Models.Producto;
import com.formacionbdi.springboot.app.item.Services.IItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
//@RequestMapping("/Items")
public class itemController {
    @Autowired
    @Qualifier("itemServiceFeign")//
    private IItemService iItemService;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory; //Esta es un forma de varias para implementar el patron

    @Value("${configuracion.texto}")//también podrian ir como parametro donde se utilizan
    private String texto;
    @Value("${server.port}")//también podrian ir como parametro donde se utilizan
    private String puerto;

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
    //                  CONTIENE EJEMPLO DE CIRCUITBREAKER FACTORY: en el cual podemos usar los parametros por defecto o
    //                  configurarlos nosotros (Bean en la clase RestTemplateConfig )
    @GetMapping("/ItemById/{id}/Cantidad/{cantidad}")
    public ResponseEntity<Item> itemById(@PathVariable Long id,@PathVariable Integer cantidad){
        return circuitBreakerFactory.create("items")//Este es el nombre del cortocircuito y luego el .run() donde usamos una expresion lambda
                .run(()->ResponseEntity.ok(iItemService.finById(id,cantidad)), e -> metodoAlternativo(id,cantidad)); //en caso de error "e" llamara al metodo alternativo;
        // de no tenerlo, cada vez que abra el cortocircuito o se envie una solicitud insatisfactoria devolvera un error "NoFallBackAvailableException"
    }


    //-----------------------------------------------------------------------------------------------------------------------
    //                  CONTIENE EJEMPLO DE CIRCUITBREAKER utilizando anotaciones.
    //                      esto es pruedente hacerlo en el servicio, aca hacemos foco en el funcionamiento
    //                      SIEMPRE que utilizamos anotaciones las configuraciones deben estar si o si en el yml y no puede
    //                      ser implementado con un @Bean
    @CircuitBreaker(name="items", fallbackMethod = "metodoAlternativo")
    @GetMapping("/ItemById2/{id}/Cantidad/{cantidad}")
    public ResponseEntity<Item> itemById2(@PathVariable Long id,@PathVariable Integer cantidad){
        return ResponseEntity.ok(iItemService.finById(id,cantidad));
    }

    //-----------------------------------------------------------------------------------------------------------------------
    //                  CONTIENE EJEMPLO DE CIRCUITBREAKER utilizando anotaciones.
    //                      acá implementamos timeout con anotaciones, usamos el @TimeLimiter!
    @TimeLimiter(name = "items", fallbackMethod = "metodoAlternativo2")
    @GetMapping("/ItemById3/{id}/Cantidad/{cantidad}")
    public CompletableFuture<ResponseEntity<Item>> itemById3(@PathVariable Long id, @PathVariable Integer cantidad){
        return CompletableFuture.supplyAsync(()->
                ResponseEntity.ok(iItemService.finById(id,cantidad))); //CONSUMO ASINCRONICO!!
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
    public CompletableFuture<ResponseEntity<Item>> metodoAlternativo2(Long id, Integer cantidad){
        Item item = new Item();
        Producto producto = new Producto();
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return CompletableFuture.supplyAsync(()->ResponseEntity.ok(item));
    }

    //-----------------------------------------------------------------------------------------------------------------------
    //--------------------------PARA OBSERVAR MEJOR EL COMPORTAMIENTO DE SERVICE-CONFIG DECLARAMOS ESTE CONTROLADOR
    //--------------------------el unico fin es ver el comportamiento de la configuracion

    @GetMapping("/obtener-configuracion")
    public ResponseEntity<?> obtenerConfig(){
        Map<String,String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("puerto",puerto);
        return ResponseEntity.ok(json);
    }
}
