package com.example.restaurant.entity;

import com.example.restaurant.entity.enums.Role;
import com.example.restaurant.entity.enums.types.RoleType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Type;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.Table;
import javax.persistence.Column;

@org.hibernate.annotations.TypeDef(name="role", typeClass = RoleType.class)

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "user", schema="public")
public class User {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", updatable = true)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "email", updatable = false)
    @Email(message = "Invalid email address")
    @NotBlank(message = "Name is required")
    private String email;

    @Column(name = "cash", updatable = true)
    private String cash;

    @Column(name = "salt", updatable = true)
    private String salt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", updatable = true)
    @Type(type = "role")
    private Role role = Role.USER;

    @Column(name = "auth", updatable = true)
    private String auth;

    public Boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.cash);
    }

    public User(String name, String email, String password) {
        String salt = BCrypt.gensalt();

        this.name  = name;
        this.email = email;
        this.cash  = BCrypt.hashpw(password, salt);
        this.salt  = salt;
        this.role  = Role.USER;
    }

    public User(String name, String email, String password, String salt) {
        this.name  = name;
        this.email = email;
        this.cash  = BCrypt.hashpw(password, salt);
        this.salt  = salt;
        this.role  = Role.USER;
    }
}
