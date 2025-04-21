package com.example.AxmCarService.repository;

import com.example.AxmCarService.domain.Workshop;

import java.util.List;

public interface WorkshopRepository {
    List<Workshop> getAllWorkshops();

    Workshop create(Workshop workshop);

    void deleteWorkshop(Long id);

    Workshop edit(Workshop workshop);

}
