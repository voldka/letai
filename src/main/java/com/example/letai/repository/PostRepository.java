package com.springdoan.demo.repository;

import com.springdoan.demo.entity.PostEntity;
import org.springframework.data.repository.Repository;

public interface PostRepository extends Repository<PostEntity,Long> {
    PostEntity save(PostEntity postEntity);
    PostEntity findAllById(Long id);
}
