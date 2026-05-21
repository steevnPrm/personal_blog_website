package com.github.steevnprm.personalwebsite.api.studycase;

import java.time.Instant; 
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.github.steevnprm.personalwebsite.api.section.SectionEntity;


@Getter
@Setter
@Entity
@Table(name = "study_cases")
@EntityListeners(AuditingEntityListener.class) 
public class StudyCaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Size(min = 3, max = 120)
    private String title;

    @OneToMany(mappedBy = "studyCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectionEntity> sections = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}