package com.emanuel.songsify.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    public Post findById(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            return post.get();
        } else {
            throw new PostNotFoundException(id);
        }
    }

    public Post create(Post post) {
        post.setCreationDate(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    public Post update(Long id, Post updatedPost) {
        Optional<Post> existingPost = postRepository.findById(id);

        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setAuthor(updatedPost.getAuthor());
            post.setSongName(updatedPost.getSongName());
            post.setSingerName(updatedPost.getSingerName());
            post.setSongUrl(updatedPost.getSongUrl());
            post.setDescription(updatedPost.getDescription());

            Post savedPost = postRepository.save(post);
            return savedPost;
        } else {
            throw new PostNotFoundException(id);
        }
    }

    public void delete(Long id) {
        boolean exists = postRepository.existsById(id);

        if (exists) {
            postRepository.deleteById(id);
        } else {
            throw new PostNotFoundException(id);
        }
    }
}