package com.emanuel.songsify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emanuel.songsify.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}