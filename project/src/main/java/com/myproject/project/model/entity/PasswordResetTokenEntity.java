package com.myproject.project.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetTokenEntity {

//   private static String generateToken(){
//        return UUID.randomUUID().toString();
//    }
    @Id
    @SequenceGenerator(name = "password_reset_token_id_seq",
            allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "password_reset_token_id_seq")
    private long id;

    private String resetToken;
    private LocalDateTime created;

    public PasswordResetTokenEntity() {
//        this.resetToken = generateToken();
//        this.created = LocalDateTime.now();
    }


    public long getId() {
        return id;
    }

    public PasswordResetTokenEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getResetToken() {
        return resetToken;
    }

    public PasswordResetTokenEntity setResetToken(String resetToken) {
        this.resetToken = resetToken;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public PasswordResetTokenEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
}
