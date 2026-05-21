package com.github.steevnprm.personalwebsite.api.section;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/studycases")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping("/{studyCaseId}/sections")
    @ResponseStatus(HttpStatus.CREATED)
    public SectionResponse addSection(
            @PathVariable UUID studyCaseId,
            @Valid @RequestBody SectionCreateRequest request) {
        
        return sectionService.addSection(studyCaseId, request);
    }
}