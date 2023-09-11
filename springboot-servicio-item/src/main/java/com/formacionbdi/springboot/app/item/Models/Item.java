package com.formacionbdi.springboot.app.item.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
   private Producto producto;
   private Integer cantidad;

   public Double getTotal(){
       return producto.getPrecio() * cantidad.doubleValue();
   }
}
