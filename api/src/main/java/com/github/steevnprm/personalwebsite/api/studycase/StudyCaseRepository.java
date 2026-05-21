package com.github.steevnprm.personalwebsite.api.studycase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface StudyCaseRepository extends JpaRepository<StudyCaseEntity, UUID> {

}