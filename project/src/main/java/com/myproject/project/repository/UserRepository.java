package com.myproject.project.repository;

import com.myproject.project.model.entity.PasswordResetTokenEntity;
import com.myproject.project.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity AS u " +
            "INNER JOIN u.passwordResetToken " +
            "AS t " +
            "WHERE t.resetToken = :token")
    Optional<UserEntity> findByPasswordResetToken(@Param("token") String passwordResetToken);
}
