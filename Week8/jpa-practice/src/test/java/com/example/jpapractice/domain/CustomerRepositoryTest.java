package com.example.jpapractice.domain;

import com.example.jpapractice.domain.customer.Customer;
import com.example.jpapractice.domain.customer.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void insert_test() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("first");
        customer.setLastName("last");

        // When
        customerRepository.save(customer);

        // Then
        var entity = customerRepository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }

}