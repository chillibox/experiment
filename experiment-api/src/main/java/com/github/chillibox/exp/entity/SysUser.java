package com.github.chillibox.exp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Created on 2017/7/15.</p>
 *
 * @author Gonster
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "sys_user", indexes = {
        @Index(name = "username_index", columnList = "username"),
        @Index(name = "email_index", columnList = "email")
})
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1266501731678221394L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;

    private String password;

    private String role;

    public SysUser() {
    }

    public SysUser(SysUser other) {
        this.id = other.id;
        this.username = other.username;
        this.email = other.email;
        this.password = other.password;
        this.role = other.role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
