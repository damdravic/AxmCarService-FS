package com.example.AxmCarService.repository;

import com.example.AxmCarService.domain.Technician;

import java.util.List;

public interface TechnicianRepository<T extends Technician> {
    T create (T data);

    T getTechnicianById(Long id);

    List<T> getAllTechnicians();



}
