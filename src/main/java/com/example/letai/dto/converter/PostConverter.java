package com.example.letai.dto.converter;


import com.example.letai.dto.PostDTO;
import com.example.letai.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public PostDTO toDto(PostEntity entity) {
        PostDTO result = new PostDTO();
        result.setId(entity.getId());
        result.setImg(entity.getImg());
        return result;
    }

    public PostEntity toEntity(PostDTO dto) {
        PostEntity result = new PostEntity();
        result.setId(dto.getId());
        result.setImg(dto.getImg());
        return result;
    }

    public PostEntity toEntity(PostEntity result, PostDTO dto) {
        result.setId(dto.getId());
        result.setImg(dto.getImg());
        return result;
    }
}