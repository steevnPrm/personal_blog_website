package com.github.steevnprm.personalwebsite.api.studycase;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyCaseResponse {
    private UUID id;
    private String title;
    private Instant createdAt;
    private List<Object> sections; 
}