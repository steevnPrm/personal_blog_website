package com.github.steevnprm.personalwebsite.api.section;

import com.github.steevnprm.personalwebsite.api.studycase.StudyCaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor 
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
}