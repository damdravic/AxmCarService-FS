package com.example.AxmCarService.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class Workshop {
    private Long id;
    private String name;
    private String description;

}
