package com.formacionbdi.springboot.app.item.Services.Impl;

import com.formacionbdi.springboot.app.item.Models.DTOs.ProductoDTO;
import com.formacionbdi.springboot.app.item.Models.Item;
import com.formacionbdi.springboot.app.item.Models.Producto;
import com.formacionbdi.springboot.app.item.Services.IItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        List<Producto> productos = Arrays.asList(restTemplate.getForObject("http://servicio-productos/AllProductos", Producto[].class));//si no usaramos eureka, deberia ir el nombre del dominio y el puerto para consumir clientes http. cuando usamos eureka esto lo remplazamos por el nombre del servicio que consumimos
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
        Producto producto = restTemplate.getForObject("http://servicio-productos/ProdcutoById/{id}",Producto.class,pathVariable);
        Item item = modelMapper.map(producto,Item.class);
        item.setCantidad(cantidad);
        return item;
    }

    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        String url = "http://servicio-productos/Crear";
        ResponseEntity<ProductoDTO> prod = restTemplate.postForEntity(url,productoDTO, ProductoDTO.class);
        return prod.getBody();
    }

    @Override
    public Producto update(Producto producto) {
        String url = "http://servicio-productos/Actualizar";
         restTemplate.put(url,producto,Producto.class);
        return producto;

    }

    @Override
    public Producto delete(Long id) {
        String url = "http://servicio-productos/Eliminar/{id}";
        Map<String,String> pathVariable = new HashMap<>();
        pathVariable.put("id",id.toString());
        ResponseEntity<Producto> prodDeleted = restTemplate.getForEntity("http://servicio-productos/ProdcutoById/{id}",Producto.class,pathVariable);
        if(prodDeleted != null){
            restTemplate.delete(url, pathVariable);
            return prodDeleted.getBody();
        }
        else {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
    }
}
