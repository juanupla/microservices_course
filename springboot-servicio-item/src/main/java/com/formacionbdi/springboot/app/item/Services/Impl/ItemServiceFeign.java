package com.formacionbdi.springboot.app.item.Services.Impl;

import com.formacionbdi.springboot.app.item.Clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.Models.DTOs.ProductoDTO;
import com.formacionbdi.springboot.app.item.Models.Item;
import com.formacionbdi.springboot.app.item.Models.Producto;
import com.formacionbdi.springboot.app.item.Services.IItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
//@Primary //notacion para que se inyecte por defecto a la interfaz IItemService y sea esta la implementacion por defecto, ya que existen 2 implementaciones.
// otra opcion es cuando inyectamos la interfaz en el controller usar la notacion @Qualifire("itemServiceFeign") y usamos esta implementacion. Notar que el nombre va en minuscula
// tal cual como se llama la implementacion(tambien podemos abrir parentesis en la notacion @Service y llamarlo de otra forma ej: @Service("serviceFeign"))
//en resumen, @Qualifire es una notacion importante para decidir cuál implementacion vamos a utilizar y donde estan las implementaciones, en la notacion @Service podemos ponerle otro nombre si no deseamos usar el nombre de la clase que lo implemeta
public class ItemServiceFeign implements IItemService {
    @Autowired
    private ProductoClienteRest productoClienteRest;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<Item> finAll() {
        List<Producto> productos = productoClienteRest.productos();
        List<Item> items = new ArrayList<>();
        if(!productos.isEmpty()){
            int i = 0;
            for (Producto p: productos) {
                items.add(modelMapper.map(p,Item.class));
                items.get(i).setCantidad(1);//para este ejemplo tal como en el curso se muestra, simplificamos y dejamos cantidad =1 por defecto para todos
                i++;
            }
            return items;
        }
        else {
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Item finById(Long id, Integer cantidad) {
        Producto producto = productoClienteRest.producto(id);
        Item item = modelMapper.map(producto,Item.class);
        item.setCantidad(cantidad);
        return item;
    }

    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        return productoClienteRest.create(productoDTO);

    }

    @Override
    public Producto update(Producto producto) {
        return productoClienteRest.update(producto);
    }

    @Override
    public Producto delete(Long id) {
        return productoClienteRest.delete(id);
    }
}
