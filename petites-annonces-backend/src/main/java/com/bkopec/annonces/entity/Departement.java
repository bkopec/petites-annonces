package com.bkopec.annonces.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty; // <--- ADD THIS IMPORT

@Entity
@Table(name = "departements")
@Data
@NoArgsConstructor
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 5)
    private String code;

    @Column(name = "dep_name", nullable = false, length = 100)
    @JsonProperty("dep_name")
    private String depName;

    @Column(name = "reg_name", nullable = false, length = 100)
    @JsonProperty("reg_name")
    private String regName;

    public Departement(String code, String depName, String regName) {
        this.code = code;
        this.depName = depName;
        this.regName = regName;
    }
}