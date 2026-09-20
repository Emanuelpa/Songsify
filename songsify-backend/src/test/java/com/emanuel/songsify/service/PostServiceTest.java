package com.emanuel.songsify.service;

import com.emanuel.songsify.dto.PostRequest;
import com.emanuel.songsify.dto.PostResponse;
import com.emanuel.songsify.exception.PostNotFoundException;
import com.emanuel.songsify.model.Post;
import com.emanuel.songsify.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setTitle("OK Computer");
        post.setAuthor("Emanuel");
        post.setSongName("Paranoid Android");
        post.setSingerName("Radiohead");
        post.setSongUrl("https://open.spotify.com/track/123");
        post.setDescription("Un álbum increíble");
    }

    @Test
    void findByIdShouldReturnPostWhenPostExists() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostResponse result = postService.findById(1L);

        String expectedTitle = "OK Computer";
        assertThat(result.getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    void findByIdShouldThrowExceptionWhenPostDoesNotExist() {
        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        PostNotFoundException exception = null;
        try {
            postService.findById(999L);
        } catch (PostNotFoundException ex) {
            exception = ex;
        }

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).contains("999");
    }

    @Test
    void createShouldSetCreationDateBeforeSaving() {
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PostRequest newPostRequest = new PostRequest();
        PostResponse result = postService.create(newPostRequest);

        assertThat(result.getCreationDate()).isNotNull();
    }

    @Test
    void deleteShouldThrowExceptionWhenPostDoesNotExist() {
        when(postRepository.existsById(999L)).thenReturn(false);

        PostNotFoundException exception = null;
        try {
            postService.delete(999L);
        } catch (PostNotFoundException ex) {
            exception = ex;
        }

        assertThat(exception).isNotNull();
        verify(postRepository, never()).deleteById(any());
    }
}