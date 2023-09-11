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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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
}
