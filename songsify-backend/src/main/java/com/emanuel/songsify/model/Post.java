package com.emanuel.songsify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "El título es obligatorio")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    @Column(name = "author")
    private String author;

    @NotBlank(message = "El nombre de la canción es obligatorio")
    @Column(name = "song_name")
    private String songName;

    @NotBlank(message = "El nombre del cantante es obligatorio")
    @Column(name = "singer_name")
    private String singerName;

    @Column(name = "song_url")
    private String songUrl;

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}