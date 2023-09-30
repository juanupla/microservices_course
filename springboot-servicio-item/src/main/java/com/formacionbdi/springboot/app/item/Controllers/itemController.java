package com.formacionbdi.springboot.app.item.Controllers;

import com.formacionbdi.springboot.app.item.Models.Item;
import com.formacionbdi.springboot.app.item.Services.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/Items")
public class itemController {
    @Autowired
    @Qualifier("itemServiceFeign")//
    private IItemService iItemService;

    @GetMapping("/AllItems")
    public ResponseEntity<List<Item>> AllItems(@RequestParam(name="nombre",required = false) String nombre,@RequestHeader(name = "token-request",required = false) String token){
        //@RequestParam y @RequestHeader es para manejar las cabeceras establecidas en el filtro del microservico Gateway en el yml.
        // los name es como llamamos a estos request en los filtros y el required = false indicamos que no es obligatorio
        // ya que si no los ponemos y enviamos una petición sin estos request lanzaría una exepción

        System.out.println(nombre);
        System.out.println(token);    //a los request solo los mostraremos en consola
        return ResponseEntity.ok(iItemService.finAll());
    }
    @GetMapping("/ItemById/{id}/Cantidad/{cantidad}")
    public ResponseEntity<Item> itemById(@PathVariable Long id,@PathVariable Integer cantidad){
        return ResponseEntity.ok(iItemService.finById(id,cantidad));
    }
}
