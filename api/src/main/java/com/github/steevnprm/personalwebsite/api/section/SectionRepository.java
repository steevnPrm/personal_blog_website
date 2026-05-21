package com.github.steevnprm.personalwebsite.api.section;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SectionRepository extends JpaRepository<SectionEntity, UUID> {
    int countByStudyCaseId(UUID studyCaseId);
}