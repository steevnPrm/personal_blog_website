package com.github.steevnprm.personalwebsite.api.studycase;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/studycases")
public class StudyCaseController {


    @GetMapping
    public ResponseEntity<List<String>> getAllStudyCases() {
        return ResponseEntity.ok(List.of("Projet Fintech - Loan eligibility", "TrustGuard - Microservice KYB"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createStudyCase(@RequestBody String studyCasePayload) {
        return ResponseEntity.status(201).body("Study case créée avec succès !");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudyCase(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}