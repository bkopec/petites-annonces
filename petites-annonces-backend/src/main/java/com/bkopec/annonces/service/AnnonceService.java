package com.bkopec.annonces.service;

import com.bkopec.annonces.dto.AnnonceDto;
import com.bkopec.annonces.dto.DepartementDto;
import com.bkopec.annonces.dto.CreateAnnonceDto;
import com.bkopec.annonces.entity.Annonce;
import com.bkopec.annonces.entity.Departement;
import com.bkopec.annonces.exception.ResourceNotFoundException;
import com.bkopec.annonces.repository.AnnonceRepository;
import com.bkopec.annonces.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final DepartementRepository departementRepository;

    @Autowired
    public AnnonceService(AnnonceRepository annonceRepository, DepartementRepository departementRepository) {
        this.annonceRepository = annonceRepository;
        this.departementRepository = departementRepository;
    }

    @Transactional
    public AnnonceDto createAnnonce(CreateAnnonceDto createDto) {
        Departement departement = departementRepository.findByCode(createDto.getDepartementCode())
                .orElseThrow(() -> new ResourceNotFoundException("Departement not found with code: " + createDto.getDepartementCode()));

        Annonce annonce = new Annonce(
                createDto.getTitle(),
                createDto.getContent(),
                createDto.getPosterName(),
                departement
        );
        Annonce savedAnnonce = annonceRepository.save(annonce);
        return mapToDto(savedAnnonce);
    }

    @Transactional(readOnly = true)
    public Page<AnnonceDto> getAnnonces(Pageable pageable, String departementCode) {
        Page<Annonce> annoncesPage;
        if (departementCode != null && !departementCode.isEmpty()) {
            annoncesPage = annonceRepository.findByDepartementCode(departementCode, pageable);
        } else {
            annoncesPage = annonceRepository.findAll(pageable);
        }
        return annoncesPage.map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public AnnonceDto getAnnonceById(Long id) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce not found with id: " + id));
        return mapToDto(annonce);
    }

    @Transactional(readOnly = true)
    public List<DepartementDto> getAllDepartements() {
        return departementRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    private AnnonceDto mapToDto(Annonce annonce) {
        return new AnnonceDto(
                annonce.getId(),
                annonce.getTitle(),
                annonce.getContent(),
                annonce.getPosterName(),
                annonce.getDepartement().getCode(),
                annonce.getDepartement().getDepName(),
                annonce.getCreatedAt()
        );
    }

    private DepartementDto mapToDto(Departement departement) {
        return new DepartementDto(
                departement.getId(),
                departement.getCode(),
                departement.getDepName(),
                departement.getRegName()
        );
    }
}