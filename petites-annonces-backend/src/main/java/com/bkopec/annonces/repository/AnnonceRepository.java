package com.bkopec.annonces.repository;

import com.bkopec.annonces.entity.Annonce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    Page<Annonce> findByDepartementCode(String departementCode, Pageable pageable);

    Page<Annonce> findByDepartementId(Long departementId, Pageable pageable);
}