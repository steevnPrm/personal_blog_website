package com.github.steevnprm.personalwebsite.api.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, UUID> {
    // Permet de récupérer les sections d'une étude de cas spécifique, ordonnées
    List<SectionEntity> findByStudyCaseIdOrderByPositionAsc(UUID studyCaseId);
}