package com.example.letai.model.dto.converter;


import com.example.letai.model.dto.PostDTO;
import com.example.letai.model.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public PostDTO toDto(PostEntity entity) {
        PostDTO result = new PostDTO();
        result.setId(entity.getId());
        result.setImg(entity.getImg());
        result.setComment(entity.getComment());
        result.setPostName(entity.getPostName());
        result.setCreatedAt(entity.getCreatedAt());
        result.setUpdatedAt(entity.getUpdatedAt());
        return result;
    }

    public PostEntity toEntity(PostDTO dto) {
        PostEntity result = new PostEntity();
        result.setId(dto.getId());
        result.setImg(dto.getImg());
        result.setComment(dto.getComment());
        result.setPostName(dto.getPostName());
        result.setCreatedAt(dto.getCreatedAt());
        result.setUpdatedAt(dto.getUpdatedAt());
        return result;
    }

    public PostEntity toEntity(PostEntity result, PostDTO dto) {
        result.setId(dto.getId());
        result.setImg(dto.getImg());
        result.setComment(dto.getComment());
        result.setPostName(dto.getPostName());
        result.setCreatedAt(dto.getCreatedAt());
        result.setUpdatedAt(dto.getUpdatedAt());
        return result;
    }
}
