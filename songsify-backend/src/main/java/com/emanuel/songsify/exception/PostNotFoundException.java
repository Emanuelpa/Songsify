package com.emanuel.songsify.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("No se encontró un post con el id " + id);
    }
}