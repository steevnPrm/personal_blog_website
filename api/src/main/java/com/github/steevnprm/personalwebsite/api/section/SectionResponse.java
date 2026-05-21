package com.github.steevnprm.personalwebsite.api.section;

import java.util.UUID;

public record SectionResponse(
    UUID id,
    String subtitle,
    String content,
    Integer position
) {}