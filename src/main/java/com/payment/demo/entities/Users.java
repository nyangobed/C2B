package com.payment.demo.entities;

import com.payment.demo.models.LoginRequest;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String password;
    private String Firstname;
    private String Lastname;
    private Date CreatedOn;

    @Column(name = "email", nullable = true, length = 250)
    @Size(max=50)
    @Size(min=10)
    @NotBlank(message = "Username mandatory")
    @NotNull
    @Pattern(regexp = "^([^ @])+@([^ \\.@]+\\.)+([^ \\.@])+$")
    public String getEmail() {
        return email;
    }

    public Users setEmail(String email) {
        this.email = email;
        return this;
    }



    public long getId() {
        return id;
    }

    public Users setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Users setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Users setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstname() {
        return Firstname;
    }

    public Users setFirstname(String firstname) {
        Firstname = firstname;
        return this;
    }

    public String getLastname() {
        return Lastname;
    }

    public Users setLastname(String lastname) {
        Lastname = lastname;
        return this;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public Users setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
        return this;
    }
}
