package com.myapp.sendit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.sendit.model.Customer;



@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}