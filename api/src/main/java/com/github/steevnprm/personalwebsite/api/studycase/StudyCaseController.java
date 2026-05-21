package com.github.steevnprm.personalwebsite.api.studycase;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/studycases")
public class StudyCaseController {

    private final StudyCaseService studyCaseService;

    public StudyCaseController(StudyCaseService studyCaseService) {
        this.studyCaseService = studyCaseService;
    }

    /**
     * Récupère la liste de toutes les études de cas disponibles.
     * Accès : Public (Aucun token requis)
     */
    @GetMapping
    public ResponseEntity<List<StudyCaseResponse>> getAll() {
        return ResponseEntity.ok(studyCaseService.getAll());
    }

    /**
     * Récupère les détails complets d'une étude de cas spécifique par son ID.
     * Accès : Public (Aucun token requis)
     */
    @GetMapping("/{studyCaseId}")
    public ResponseEntity<StudyCaseResponse> getById(@PathVariable UUID studyCaseId) {
        StudyCaseResponse response = studyCaseService.getById(studyCaseId);
        return ResponseEntity.ok(response);
    }

    /**
     * Crée une nouvelle étude de cas.
     * Accès : Restreint (Rôle ADMIN requis)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudyCaseResponse> create(@Valid @RequestBody StudyCaseCreateRequest request) {
        StudyCaseResponse response = studyCaseService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}