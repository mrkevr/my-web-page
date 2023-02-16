package com.mrkevr.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrkevr.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
