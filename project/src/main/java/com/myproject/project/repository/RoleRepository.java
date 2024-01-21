package com.myproject.project.repository;

import com.myproject.project.model.entity.RoleEntity;
import com.myproject.project.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(RoleEnum roleEnum);
}
