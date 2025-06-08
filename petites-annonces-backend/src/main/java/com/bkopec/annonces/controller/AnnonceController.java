package com.bkopec.annonces.controller;

import com.bkopec.annonces.dto.AnnonceDto;
import com.bkopec.annonces.dto.DepartementDto;
import com.bkopec.annonces.dto.CreateAnnonceDto;
import com.bkopec.annonces.service.AnnonceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {

    private final AnnonceService annonceService;

    @Autowired
    public AnnonceController(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

    @PostMapping
    public ResponseEntity<AnnonceDto> createAnnonce(@Valid @RequestBody CreateAnnonceDto createAnnonceDto) {
        AnnonceDto createdAnnonce = annonceService.createAnnonce(createAnnonceDto);
        return new ResponseEntity<>(createdAnnonce, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AnnonceDto>> getAnnonces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String departementCode) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AnnonceDto> annoncesPage = annonceService.getAnnonces(pageable, departementCode);
        return ResponseEntity.ok(annoncesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnonceDto> getAnnonceById(@PathVariable Long id) {
        AnnonceDto annonce = annonceService.getAnnonceById(id);
        return ResponseEntity.ok(annonce);
    }

    @GetMapping("/departements")
    public ResponseEntity<List<DepartementDto>> getAllDepartements() {
        List<DepartementDto> departements = annonceService.getAllDepartements();
        return ResponseEntity.ok(departements);
    }
}