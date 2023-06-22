package com.myproject.project.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetTokenEntity {

    @Id
    @SequenceGenerator(name = "password_reset_token_id_seq",
            allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "password_reset_token_id_seq")
    private long id;

    private String resetToken;
    private LocalDateTime created;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private UserEntity user;
}
