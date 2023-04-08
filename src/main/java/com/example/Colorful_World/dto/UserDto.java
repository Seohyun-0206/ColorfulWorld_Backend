package com.example.Colorful_World.dto;

import com.example.Colorful_World.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private int intensity;

    public UserDto(String email, String password, int intensity){
        this.email = email;
        this.password = password;
        this.intensity = intensity;
    }

    public UserEntity toEntity(){

        UserEntity userEntity = new UserEntity().builder()
                .email(email)
                .password(password)
                .intensity(intensity)
                .build();

        return userEntity;
    }
}
