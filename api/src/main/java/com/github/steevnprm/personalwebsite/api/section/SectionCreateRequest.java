package com.github.steevnprm.personalwebsite.api.section;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectionCreateRequest {

    @NotBlank
    @Size(min = 3, max = 120)
    private String subtitle;

    @NotBlank(message = "Le contenu est obligatoire")
    private String content;
}