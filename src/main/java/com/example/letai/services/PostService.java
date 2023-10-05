package com.example.letai.services;


import com.example.letai.exception.exceptionhandler.ResourceNotFoundException;
import com.example.letai.model.dto.PostDTO;
import com.example.letai.model.dto.ProductDTO;
import com.example.letai.model.dto.converter.PostConverter;
import com.example.letai.model.entity.PostEntity;
import com.example.letai.model.entity.ProductEntity;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<PostDTO> findAll() {
        List<PostDTO> list = new ArrayList<>();
        Iterable<PostEntity> listPost = postRepository.findAll();
        for (PostEntity post : listPost) {
            list.add(postConverter.toDto(post));
        }
        return list;
    }
    public Page<PostDTO> findAll(Pageable pageable) {
        Page<PostEntity> rs = postRepository.findAll(pageable);
        Page<PostDTO> dtos = rs.map(productEntity -> {
            return postConverter.toDto(productEntity);
        });
        return dtos;
    }
    public PostDTO findById(Long id){
        PostEntity rs = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));;
        return postConverter.toDto(rs);
    }

    public PostDTO updatePost(Long idpost,PostDTO postDetails){
        PostEntity post = postRepository.findById(idpost)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", idpost));
        post.setPostName(postDetails.getPostName());
        post.setComment(postDetails.getComment());
        post.setImg(postDetails.getImg());

        PostEntity updatedpost = postRepository.save(post);

        PostDTO rs = postConverter.toDto(updatedpost);
        return rs;
    }
    public void deletePostById(Long commentId){
        PostEntity comment = postRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        postRepository.delete(comment);
    }
}
