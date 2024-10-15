package com.example.AxmCarService.domain;

import lombok.Data;

@Data
public class Technician {

    private Long technicianId;
    private String technicianSpecialization;
    private int technicianExperience;
    private Long technicianWorkshopId;
    private Long technicianUserId;


}
