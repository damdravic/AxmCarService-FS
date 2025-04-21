package com.example.AxmCarService.repository;

import com.example.AxmCarService.domain.Customer;

import java.util.List;

public interface CustomerRepository<T extends Customer>{
    public T getCustomerById(Long id);
    public T create(T order);
    public T update(T order);
    public void delete(Long id);
    public List<T> getAll();

}
