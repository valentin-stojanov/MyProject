package com.myproject.project.repository;

import com.myproject.project.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity AS u " +
            "INNER JOIN u.passwordResetToken " +
            "AS t " +
            "WHERE t.resetToken = :token AND " +
            "EXTRACT(EPOCH FROM ( :currentTime - t.created )) <= :tokenExpirationSeconds")
    Optional<UserEntity> findByPasswordResetToken(@Param("token") String passwordResetToken,
                                                  @Param("currentTime") LocalDateTime currentTime,
                                                  @Param("tokenExpirationSeconds") int tokenExpirationSeconds);

    @Modifying
    @Transactional
    @Query(value = "WITH updated_users AS " +
                "( UPDATE users " +
                "  SET password_reset_token_id = null " +
                "  WHERE id IN (SELECT prt.user_id " +
            "                   FROM password_reset_token AS prt " +
            "                   WHERE prt.created < CURRENT_TIMESTAMP - INTERVAL '?1 seconds') " + " RETURNING id" +
                                ") " +
            "DELETE FROM password_reset_token " +
            "WHERE user_id IN (SELECT id FROM updated_users)", nativeQuery = true)
    void deleteExpiredPasswordResetToken(int expirationTimeInSeconds);
}
