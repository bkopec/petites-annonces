package com.bkopec.annonces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnonceDto {
    private Long id;
    private String title;
    private String content;
    private String posterName;
    private String departementCode;
    private String departementName;
    private LocalDateTime createdAt;
}