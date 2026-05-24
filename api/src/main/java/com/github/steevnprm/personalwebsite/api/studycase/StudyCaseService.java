package com.github.steevnprm.personalwebsite.api.studycase;

import com.github.steevnprm.personalwebsite.api.exception.BusinessException;
import com.github.steevnprm.personalwebsite.api.exception.ErrorCode;
import com.github.steevnprm.personalwebsite.api.section.SectionResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StudyCaseService {

    private final StudyCaseRepository studyCaseRepository;

    public StudyCaseService(StudyCaseRepository studyCaseRepository) {
        this.studyCaseRepository = studyCaseRepository;
    }

    @Transactional
    public StudyCaseResponse create(StudyCaseCreateRequest request) {
        StudyCaseEntity entity = new StudyCaseEntity();
        entity.setTitle(request.getTitle().trim());
        
        StudyCaseEntity saved = studyCaseRepository.save(entity);
        
        return new StudyCaseResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getCreatedAt(),
                Collections.emptyList() 
        );
    }

    @Transactional(readOnly = true)
    public StudyCaseResponse getById(UUID id) {
        StudyCaseEntity entity = studyCaseRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                    ErrorCode.STUDYCASE_NOT_FOUND, 
                    Map.of("id", id)
                ));
        
        List<SectionResponse> sections = entity.getSections() == null ? Collections.emptyList() :
                entity.getSections().stream()
                .map(s -> new SectionResponse(
                        s.getId(), 
                        s.getSubtitle(), 
                        s.getContent(), 
                        s.getPosition()
                ))
                .toList();
        
        return new StudyCaseResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getCreatedAt(),
                sections
        );
    }

    @Transactional(readOnly = true)
    public List<StudyCaseResponse> getAll() {
        return studyCaseRepository.findAll().stream()
                .map(entity -> new StudyCaseResponse(
                    entity.getId(),
                    entity.getTitle(),
                    entity.getCreatedAt(),
                    Collections.emptyList() 
                ))
                .toList();
    }
}