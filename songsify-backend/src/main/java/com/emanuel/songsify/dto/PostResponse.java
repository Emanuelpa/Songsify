package com.emanuel.songsify.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String author;
    private String songName;
    private String singerName;
    private String songUrl;
    private String description;
    private LocalDateTime creationDate;
}