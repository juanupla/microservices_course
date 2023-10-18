package com.formacionbdi.springboot.app.productos.Services.impl;

import com.formacionbdi.springboot.app.productos.Entities.ProductoEntity;
import com.formacionbdi.springboot.app.productos.Models.DTOs.ProductoDTO;
import com.formacionbdi.springboot.app.productos.Models.Producto;
import com.formacionbdi.springboot.app.productos.Repositories.ProductoJpa;
import com.formacionbdi.springboot.app.productos.Services.IProductoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductoJpa productoJpa;

    @Override
    public List<ProductoDTO> findAll() {
        List<ProductoDTO> prod = new ArrayList<>();
        List<ProductoEntity> productoEntities = productoJpa.findAll();
        if(!productoEntities.isEmpty()){
            for (ProductoEntity pe: productoEntities) {
                prod.add(modelMapper.map(pe,ProductoDTO.class));
            }
            return prod;
        }
        else {
            throw new ErrorResponseException(HttpStatus.NO_CONTENT);
        }

    }

    @Override
    public ProductoDTO findById(Long id) {
        ProductoEntity productoEntity = productoJpa.getReferenceById(id);
        if(productoEntity != null){
            return modelMapper.map(productoEntity,ProductoDTO.class);
        }
        else {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        ProductoEntity productoEntity = modelMapper.map(productoDTO,ProductoEntity.class);
        ProductoDTO product = (modelMapper.map(productoJpa.save(productoEntity),ProductoDTO.class));
        if(product != null){
            return product;
        }
        else {
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Producto update(Producto producto) {
        ProductoEntity produ = productoJpa.getReferenceById(producto.getId());
        if(produ != null){
            return modelMapper.map(productoJpa.save(modelMapper.map(producto,ProductoEntity.class)),Producto.class);
        }
        else {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Producto delete(Long id) {
        ProductoEntity productoEntity = productoJpa.getReferenceById(id);
        if (productoEntity != null){
            productoJpa.deleteById(id);
            return modelMapper.map(productoEntity,Producto.class);
        }
        else {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
    }


}
