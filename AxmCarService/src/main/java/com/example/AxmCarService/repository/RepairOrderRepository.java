package com.example.AxmCarService.repository;

import com.example.AxmCarService.domain.RepairOrder;

import java.text.ParseException;
import java.util.List;

public interface RepairOrderRepository <T extends RepairOrder> {


     T create (T data);


    List<T> getAll() ;
    // Implement methods for CRUD operations on RepairOrder entity
}
