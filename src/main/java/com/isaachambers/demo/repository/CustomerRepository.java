package com.isaachambers.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isaachambers.demo.domain.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
