package com.bkopec.annonces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAnnonceDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotBlank(message = "Poster name cannot be blank")
    @Size(max = 100, message = "Poster name cannot exceed 100 characters")
    private String posterName;

    @NotBlank(message = "Departement code cannot be blank")
    private String departementCode;
}