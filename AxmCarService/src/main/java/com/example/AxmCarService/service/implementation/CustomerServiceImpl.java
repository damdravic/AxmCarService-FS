package com.example.AxmCarService.service.implementation;

import com.example.AxmCarService.domain.Customer;
import com.example.AxmCarService.repository.CustomerRepository;
import com.example.AxmCarService.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Override
    public Customer addCustomer(Customer customer) {

        return customerRepository.create(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer getCustomer(Long customerId) {
        return null;
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.delete(customerId);
    }

    @Override
    public void deleteAllCustomers() {


    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {

        return customerRepository.getAll();
    }
}
