package com.markswell.auth.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "permissions")
public class Permissions implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 6127531798267373826L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;


    @Override
    public String getAuthority() {
        return this.description;
    }
}
