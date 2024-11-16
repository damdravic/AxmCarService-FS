package com.example.AxmCarService.service;

import com.example.AxmCarService.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {


    public Customer addCustomer(Customer customer);

    public Customer updateCustomer(Customer customer);

    public Customer getCustomer(Long customerId);

    public void deleteCustomer(Long customerId);

    public void deleteAllCustomers();

    public Customer getCustomerByEmail(String email);

    public List<Customer> getAllCustomers();


}
