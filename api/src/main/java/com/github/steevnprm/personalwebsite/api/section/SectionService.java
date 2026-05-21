package com.github.steevnprm.personalwebsite.api.section;


import com.github.steevnprm.personalwebsite.api.studycase.StudyCaseEntity;
import com.github.steevnprm.personalwebsite.api.studycase.StudyCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final StudyCaseRepository studyCaseRepository;

    @Transactional
    public SectionResponse addSection(UUID studyCaseId, SectionCreateRequest request) {

        StudyCaseEntity studyCase = studyCaseRepository.findById(studyCaseId)
                .orElseThrow(() -> new IllegalArgumentException("Étude de cas introuvable"));

        int newPosition = sectionRepository.countByStudyCaseId(studyCaseId) + 1;

        SectionEntity section = new SectionEntity();
        section.setStudyCase(studyCase);
        section.setSubtitle(request.getSubtitle().trim());
        section.setContent(request.getContent().trim());
        section.setPosition(newPosition);

        SectionEntity saved = sectionRepository.save(section);

        return new SectionResponse(
                saved.getId(),
                saved.getSubtitle(),
                saved.getContent(),
                saved.getPosition()
        );
    }
}