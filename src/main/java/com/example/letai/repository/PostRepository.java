package com.example.letai.repository;


import com.example.letai.model.entity.PostEntity;
import org.springframework.data.repository.Repository;

public interface PostRepository extends Repository<PostEntity,Long> {
    PostEntity save(PostEntity postEntity);
    PostEntity findAllById(Long id);
}
