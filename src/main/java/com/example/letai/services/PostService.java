package com.springdoan.demo.services;

import com.springdoan.demo.converter.PostConverter;
import com.springdoan.demo.dto.PostDTO;
import com.springdoan.demo.entity.PostEntity;
import com.springdoan.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostConverter postConverter;

    @Autowired
    private PostRepository postRepository;

    public PostDTO save(PostDTO postDTO){
        PostEntity postEntity = new PostEntity();
        postEntity = postConverter.toEntity(postDTO);
        PostEntity result = postRepository.save(postEntity);
        if(result != null && result.getId()!=0){
             PostDTO results = postConverter.toDto(postEntity);
            return results;
        }else{
            return null;
        }
    }
}
