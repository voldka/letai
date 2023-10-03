package com.example.letai.repository;


import com.example.letai.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
    PostEntity save(PostEntity postEntity);
    PostEntity findAllById(Long id);
}
