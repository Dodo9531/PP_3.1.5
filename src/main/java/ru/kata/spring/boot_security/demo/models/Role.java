package ru.kata.spring.boot_security.demo.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
    public Role (String rolename) {
        name = rolename;
    }
}
