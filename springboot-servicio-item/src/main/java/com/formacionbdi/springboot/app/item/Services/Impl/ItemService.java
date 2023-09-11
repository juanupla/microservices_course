package com.formacionbdi.springboot.app.item.Services.Impl;

import com.formacionbdi.springboot.app.item.Models.Item;
import com.formacionbdi.springboot.app.item.Models.Producto;
import com.formacionbdi.springboot.app.item.Services.IItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ItemService implements IItemService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<Item> finAll() {
        List<Producto> productos = Arrays.asList(restTemplate.getForObject("http://localhost:8001/Productos/AllProductos", Producto[].class));
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
        Map<String,String> pathVariable = new HashMap<String,String>();
        pathVariable.put("id",id.toString());
        Producto producto = restTemplate.getForObject("http://localhost:8001/Productos/ProdcutoById/{id}",Producto.class,pathVariable);
        Item item = modelMapper.map(producto,Item.class);
        item.setCantidad(cantidad);
        return item;
    }
}
