package com.example.AxmCarService.service;

import com.example.AxmCarService.domain.Technician;

import java.util.List;

public interface TechnicianService {

    Technician addTechnician(Technician technician);
    Technician getTechnicianById(Long technicianId);
    List<Technician> getAllTechnicians();
    List<Technician> getTechniciansByWorkshopId(Long workshopId);

    List<Technician> getTechniciansBySpecialization(String specialization);

    Technician updateTechnician(Technician technician);

    void deleteTechnician(Long technicianId);



}
