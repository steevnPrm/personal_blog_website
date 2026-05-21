package com.github.steevnprm.personalwebsite.api.section;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.github.steevnprm.personalwebsite.api.studycase.StudyCaseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sections")
@EntityListeners(AuditingEntityListener.class)
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_case_id", nullable = false)
    private StudyCaseEntity studyCase;

    @NotBlank
    @Size(min = 3, max = 120)
    @Column(nullable = false)
    private String subtitle;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Min(1)
    @Column(nullable = false)
    private Integer position;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    // Constructeurs
    public SectionEntity() {}

    // Getters et Setters
    public UUID getId() { return id; }
    
    public StudyCaseEntity getStudyCase() { return studyCase; }
    public void setStudyCase(StudyCaseEntity studyCase) { this.studyCase = studyCase; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
}