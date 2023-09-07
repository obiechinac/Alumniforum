package com.esm.alumniforum.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageResponse {
    private String imageUrl;
    private String userId;

    public ImageResponse(String imageUrl, String userId) {
        this.imageUrl=imageUrl;
        this.userId=userId;
    }
}
