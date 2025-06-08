package com.bkopec.annonces.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "annonces")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT") // Content of the ad
    private String content;

    @Column(name = "poster_name", nullable = false, length = 100)
    private String posterName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Annonce(String title, String content, String posterName, Departement departement) {
        this.title = title;
        this.content = content;
        this.posterName = posterName;
        this.departement = departement;
    }
}