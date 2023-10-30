package com.formacionbdi.sptingboot.app.usuarios.Services.impl;

import com.formacionbdi.sptingboot.app.usuarios.Config.mappersConfig;
import com.formacionbdi.sptingboot.app.usuarios.Entities.UsuarioEntity;
import com.formacionbdi.sptingboot.app.usuarios.Models.DTOs.UsuarioDto;
import com.formacionbdi.sptingboot.app.usuarios.Models.Usuario;
import com.formacionbdi.sptingboot.app.usuarios.Repositories.usuarioJpa;
import com.formacionbdi.sptingboot.app.usuarios.Services.iUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.Optional;
@Service
public class implUsuario implements iUsuario {
    @Autowired
    private usuarioJpa usuarioJpa;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDto findByUsername(String username) {
        Optional<UsuarioEntity> usuarioEntity = usuarioJpa.findByUsername(username);
        if(usuarioEntity.isPresent()){
            return modelMapper.map(usuarioEntity,UsuarioDto.class);
        }
        else {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UsuarioDto newUser(UsuarioDto usuarioDto) {
        UsuarioEntity usuarioEntity = usuarioJpa.save(modelMapper.map(usuarioDto, UsuarioEntity.class));
        if (usuarioEntity != null) {
            return modelMapper.map(usuarioEntity, UsuarioDto.class);
        }
        else {
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UsuarioDto updateUser(UsuarioDto usuarioDto) {
        Optional<UsuarioEntity> usuarioEntity = usuarioJpa.findByUsername(usuarioDto.getUserName());
        if (usuarioEntity.isPresent()){
            Usuario usr = modelMapper.map(usuarioDto,Usuario.class);
            usr.setId(usuarioEntity.get().getId());
            UsuarioEntity usuarioEntity1 = usuarioJpa.save(modelMapper.map(usr,UsuarioEntity.class));
            return modelMapper.map(usuarioEntity1,UsuarioDto.class);
        }
        else {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public UsuarioDto delete(String userName) {
        Optional<UsuarioEntity> usuarioEntity = usuarioJpa.findByUsername(userName);
        if (usuarioEntity.isPresent()){
            usuarioJpa.delete(modelMapper.map(usuarioEntity,UsuarioEntity.class));
            return modelMapper.map(usuarioEntity,UsuarioDto.class);
        }
        else {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }

    }
}
