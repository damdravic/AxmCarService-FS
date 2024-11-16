package com.example.AxmCarService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Technician {
    private Long id;
    private String techName;
    private Long userId;
    private boolean isActive;
    private String workshop;
    private int experience;
    private String specialization;
}
