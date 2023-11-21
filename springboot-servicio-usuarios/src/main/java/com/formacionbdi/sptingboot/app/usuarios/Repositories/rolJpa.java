package com.formacionbdi.sptingboot.app.usuarios.Repositories;

import com.formacionbdi.sptingboot.app.usuarios.Entities.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface rolJpa extends JpaRepository<RolEntity,Long> {

}
