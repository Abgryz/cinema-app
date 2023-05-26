package com.suitt.tables.jobTittle;

import lombok.Builder;

import java.util.List;

@Builder
public record JobTittleDto(
        String name,
        int salary,
        List<String> employees
) {
}
