package com.example.Colorful_World.dto;

import com.example.Colorful_World.entity.ImageEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageDto {

    private Long id;
    private byte[] image;

    public ImageDto(byte[] image){
        this.image = image;
    }

    public ImageEntity toEntity(){
        ImageEntity imageEntity = new ImageEntity().builder()
                .image(image)
                .build();

        return imageEntity;
    }
}
