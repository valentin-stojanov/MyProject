package com.myproject.project.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CollectionType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @SequenceGenerator(name = "users_id_seq",
    allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "users_id_seq")
    private Long id;

    @Column(columnDefinition = "boolean default true")
    private boolean isAccountNonLocked;

    private String firstName;
    private String lastName;
    //    @Positive
    private int age;

    @NotBlank
    private String password;

    //    @Column(unique = true)
    @NotBlank
    private String email;

    private LocalDateTime created;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles = new ArrayList<>();

    @OneToOne
    private PasswordResetTokenEntity passwordResetToken;

    public PasswordResetTokenEntity getPasswordResetToken() {
        return passwordResetToken;
    }

    public UserEntity setPasswordResetToken(PasswordResetTokenEntity passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String fullName) {
        this.firstName = fullName;
        return this;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserEntity setAge(int age) {
        this.age = age;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public UserEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public UserEntity setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
        return this;
    }
}
