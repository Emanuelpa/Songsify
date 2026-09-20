package com.emanuel.songsify.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanuel.songsify.dto.PostRequest;
import com.emanuel.songsify.dto.PostResponse;
import com.emanuel.songsify.exception.PostNotFoundException;
import com.emanuel.songsify.model.Post;
import com.emanuel.songsify.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResponse> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : posts) {
            PostResponse postResponse = toResponse(post);
            postResponses.add(postResponse);
        }

        return postResponses;
    }

    public PostResponse findById(Long id) {
        Post post = getPostOrThrow(id);
        PostResponse postResponse = toResponse(post);
        return postResponse;
    }

    public PostResponse create(PostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setAuthor(request.getAuthor());
        post.setSongName(request.getSongName());
        post.setSingerName(request.getSingerName());
        post.setSongUrl(request.getSongUrl());
        post.setDescription(request.getDescription());
        post.setCreationDate(LocalDateTime.now());

        Post savedPost = postRepository.save(post);
        PostResponse postResponse = toResponse(savedPost);
        return postResponse;
    }

    public PostResponse update(Long id, PostRequest request) {
        Post existingPost = getPostOrThrow(id);

        existingPost.setTitle(request.getTitle());
        existingPost.setAuthor(request.getAuthor());
        existingPost.setSongName(request.getSongName());
        existingPost.setSingerName(request.getSingerName());
        existingPost.setSongUrl(request.getSongUrl());
        existingPost.setDescription(request.getDescription());

        Post savedPost = postRepository.save(existingPost);
        PostResponse postResponse = toResponse(savedPost);
        return postResponse;
    }

    public void delete(Long id) {
        boolean exists = postRepository.existsById(id);

        if (exists) {
            postRepository.deleteById(id);
        } else {
            throw new PostNotFoundException(id);
        }
    }

    private Post getPostOrThrow(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            return post.get();
        } else {
            throw new PostNotFoundException(id);
        }
    }

    private PostResponse toResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setAuthor(post.getAuthor());
        response.setSongName(post.getSongName());
        response.setSingerName(post.getSingerName());
        response.setSongUrl(post.getSongUrl());
        response.setDescription(post.getDescription());
        response.setCreationDate(post.getCreationDate());
        return response;
    }
}