package com.example.Colorful_World.Dto;

import com.example.Colorful_World.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private int index;

    public UserDto(String email, String password, int index){
        this.email = email;
        this.password = password;
        this.index = index;
    }

    public UserEntity toEntity(){

        UserEntity userEntity = new UserEntity().builder()
                .email(email)
                .password(password)
                .index(index)
                .build();

        return userEntity;
    }
}
