package com.emanuel.songsify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    private String author;

    @NotBlank(message = "El nombre de la canción es obligatorio")
    private String songName;

    @NotBlank(message = "El nombre del cantante es obligatorio")
    private String singerName;

    @NotBlank(message = "La URL de la canción es obligatoria")
    private String songUrl;

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    private String description;
}