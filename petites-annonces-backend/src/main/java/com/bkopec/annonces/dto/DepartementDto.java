package com.bkopec.annonces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartementDto {
    private Long id;
    private String code;
    private String depName;
    private String regName;
}