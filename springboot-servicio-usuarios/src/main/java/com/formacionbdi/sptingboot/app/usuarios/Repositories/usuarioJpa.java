package com.formacionbdi.sptingboot.app.usuarios.Repositories;

import com.formacionbdi.sptingboot.app.usuarios.Entities.UsuarioEntity;
import com.formacionbdi.sptingboot.app.usuarios.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface usuarioJpa extends JpaRepository<UsuarioEntity,Long> {
    Optional<UsuarioEntity> findByUsername(String username);
    //   Esto sirve para generar la consulta! Usuario es un objetoEntity! por eso se usa el nombre de los atributos como u.username.
    //   y si hubiera mas de 2 filtros va de esta forma, ya que refiere a los parametros en orden a partir de 1
    //   @Query("select u from Usuario u where u.username=?1 and u.email=?2")
    //   Usuario obtenerPorUsername(String username,String email);
    @Query("select u from UsuarioEntity u where u.username=?1")
    Optional<UsuarioEntity> obtenerPorUsername(String username);

}
