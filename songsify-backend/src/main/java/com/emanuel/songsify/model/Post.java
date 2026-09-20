package com.emanuel.songsify.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "singer_name")
    private String singerName;

    @Column(name = "song_url")
    private String songUrl;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}