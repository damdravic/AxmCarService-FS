package com.example.AxmCarService.service.implementation;

import com.example.AxmCarService.domain.RepairOrder;
import com.example.AxmCarService.exception.ApiException;
import com.example.AxmCarService.repository.RepairOrderRepository;

import com.example.AxmCarService.service.RepairOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class RepairOrderServiceImpl implements RepairOrderService {

    private final RepairOrderRepository repairOrderRepository;

    @Override
    public List<RepairOrder> getAll() {

        return repairOrderRepository.getAll();
    }


    @Override
    public RepairOrder create(RepairOrder order) {
        return repairOrderRepository.create(order);
    }

    @Override
    public RepairOrder getById(Long id) {
        return null;
    }

    @Override
    public RepairOrder update(RepairOrder order) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
