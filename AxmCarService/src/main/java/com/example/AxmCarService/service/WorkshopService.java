package com.example.AxmCarService.service;

import com.example.AxmCarService.domain.Workshop;

import java.util.List;

public interface WorkshopService {

    List<Workshop> getAllWorkshops();

    Workshop create( Workshop workshop);

    void delete( Long id);

    Workshop edit(Workshop workshop);
}
