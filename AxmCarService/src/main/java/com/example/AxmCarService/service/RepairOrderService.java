package com.example.AxmCarService.service;

import com.example.AxmCarService.domain.RepairOrder;


import java.util.List;

public interface RepairOrderService  {

   List<RepairOrder> getAll();

    RepairOrder create(RepairOrder order );

    RepairOrder getById(Long id);

    RepairOrder update(RepairOrder order);

    void delete(Long id);




}
