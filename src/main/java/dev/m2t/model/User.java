package dev.m2t.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {

    @Id
    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    // No-arg constructor required by JPA
    public User() {
    }

    // Constructor used for creating an instance from SecurityIdentity
    public User(SecurityIdentity identity) {
        this.username = identity.getPrincipal().getName();
        this.roles = identity.getRoles();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}