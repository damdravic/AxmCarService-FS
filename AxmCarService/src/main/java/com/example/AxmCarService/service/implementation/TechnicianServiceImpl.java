package com.example.AxmCarService.service.implementation;

import com.example.AxmCarService.domain.Technician;
import com.example.AxmCarService.repository.TechnicianRepository;
import com.example.AxmCarService.service.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TechnicianServiceImpl implements TechnicianService {
    private final TechnicianRepository technicianRepository;
    @Override
    public Technician addTechnician(Technician technician) {
      return technicianRepository.create(technician);
    }

    @Override
    public Technician getTechnicianById(Long technicianId) {
        return null;
    }

    @Override
    public List<Technician> getAllTechnicians() {

        return technicianRepository.getAllTechnicians();
    }

    @Override
    public List<Technician> getTechniciansByWorkshopId(Long workshopId) {
        return null;
    }

    @Override
    public List<Technician> getTechniciansBySpecialization(String specialization) {
        return null;
    }

    @Override
    public Technician updateTechnician(Technician technician) {
        return null;
    }

    @Override
    public void deleteTechnician(Long technicianId) {

    }
}
