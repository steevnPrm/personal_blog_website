package com.github.steevnprm.personalwebsite.api.studycase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
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
        
        StudyCaseResponse response = new StudyCaseResponse();
        response.setId(saved.getId());
        response.setTitle(saved.getTitle());
        response.setCreatedAt(saved.getCreatedAt());
        return response;
    }

    @Transactional(readOnly = true)
    public StudyCaseResponse getById(UUID id) {

        StudyCaseEntity entity = studyCaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Study case not found with id: " + id));
        
        StudyCaseResponse response = new StudyCaseResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setCreatedAt(entity.getCreatedAt());
        response.setSections(List.of()); 
        return response;
    }

    @Transactional(readOnly = true)
    public List<StudyCaseResponse> getAll() {
        return studyCaseRepository.findAll().stream()
                .map(entity -> {
                    StudyCaseResponse response = new StudyCaseResponse();
                    response.setId(entity.getId());
                    response.setTitle(entity.getTitle());
                    response.setCreatedAt(entity.getCreatedAt());
                    return response;
                })
                .toList();
    }
}