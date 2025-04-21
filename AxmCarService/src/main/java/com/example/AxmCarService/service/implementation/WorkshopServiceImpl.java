package com.example.AxmCarService.service.implementation;

import com.example.AxmCarService.domain.Workshop;
import com.example.AxmCarService.repository.WorkshopRepository;
import com.example.AxmCarService.service.WorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;
    @Override
    public List<Workshop> getAllWorkshops() {
        return workshopRepository.getAllWorkshops();
    }

    @Override
    public Workshop create(Workshop workshop) {
        return workshopRepository.create(workshop);
    }

    @Override
    public void delete(Long id) {
         workshopRepository.deleteWorkshop(id);
    }

    @Override
    public Workshop edit(Workshop workshop) {
        return workshopRepository.edit(workshop);
    }
}
