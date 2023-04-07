package com.example.Colorful_World.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private int intensity;

    @Builder
    public UserEntity(String email, String password, int intensity){
        this.email = email;
        this.password = password;
        this.intensity = intensity;
    }

}
